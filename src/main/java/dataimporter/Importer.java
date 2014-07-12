package dataimporter;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

import domain.Venue;
import eventbriteapi.EventbriteApi;
import eventbriteapi.EventbriteEvent;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jdom.Element;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import domain.VEvent;
import ptvapi.PTVApi;
import ptvapi.PTVResultsSet;
import ptvapi.PTVSearchRecord;
import ptvapi.PTVStop;

public class Importer {

    public static void main(String[] args) {
        // Initialize and parse the tags file:
        TagsParser tagsParser = new TagsParser();

        //List<VEvent> listOfEvents = getVEventsFromRSS("http://10.106.100.227:8080/GovHack/InEvent/eventsrss.xml");
        System.out.println("Importing events from Events Victoria...");
        List<VEvent> listOfEvents = getVEventsFromRSS("http://www.eventsvictoria.com/distributionservice/rss.xml");
        System.out.println("Done. Now importing events from Eventbrite...");
        listOfEvents.addAll(getVEventsFromEventbrite());
        System.out.println("Done.");

        System.out.println("Now indexing events in ElasticSearch...");
        BulkIndexer bulkIndexer = new BulkIndexer();
        bulkIndexer.indexEvents(listOfEvents);
        System.out.println("Done.");
    }

    private static List<VEvent> getVEventsFromRSS(String rssURL) {
        SyndFeed f = GetRSSFeed(rssURL);

        List<VEvent> eventList = new LinkedList<VEvent>();

        for (SyndEntryImpl entry : (List<SyndEntryImpl>)f.getEntries()) {
            eventList.addAll(new EntryProcessor(entry).getVEvents());
        }

        return eventList;
    }

    /**
     * Requires configuration file "eventbrite-key.properties" in the classpath
     * @return list of eventbrite events
     */
    private static List<VEvent> getVEventsFromEventbrite() {
        List<VEvent> eventList = new LinkedList<VEvent>();
        try {
            String oAuthToken = (new PropertiesConfiguration("eventbrite-key.properties")).getString("oauth-token");
            EventbriteApi eventbriteApi = new EventbriteApi(oAuthToken);

            Set<EventbriteEvent> ebResults = eventbriteApi.getBasicEventsJson("melbourne", "20km", false);
            System.out.println("Eventbrite events fetched. Performing postprocessing on Eventbrite events...");

            for (EventbriteEvent event : ebResults) {
                VEvent vEvent = new VEvent();

                vEvent.setId(event.id);
                vEvent.setTitle(event.name.text);
                vEvent.setLink(event.resource_uri);
                vEvent.setUrl(event.resource_uri);
                vEvent.setDescription(event.description.text);
                vEvent.setCategory("EVENTBRITE");
                vEvent.setType("EVENTBRITE");

                List<String> tags = new LinkedList<String>();
                if (event.category != null) {
                    tags.add(event.category.name);
                }
                vEvent.setTag(tags);

                Venue venue = new Venue();
                venue.setSuburb(event.venue.address.city);
                venue.setAddressLine1(event.venue.address.address_1);
                venue.setName(event.venue.name);
                venue.setLat(event.getCoords().getLat());
                venue.setLon(event.getCoords().getLon());
                vEvent.setVenue(venue);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+10"));
                try {
                    long date = dateFormat.parse(event.start.local).getTime();// / 1000; // Unit time is in seconds
                    vEvent.setEventDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                List<String> imageList = new LinkedList<String>();
                imageList.add("http://blogmedia.eventbrite.com/wp-content/uploads/event-brite.jpeg");
                vEvent.setImageUrls(imageList);
                vEvent.setPriceKnown(false); // TODO: Get proper pricing from eventbrite
                vEvent.setFree(false);

                // Set nearby public transport options
                PTVApi ptvApi = new PTVApi();
                PTVResultsSet nearestPublicTransport = ptvApi.getNearestTransport(vEvent.getVenue());
                PTVStop nearestTrain = null;
                PTVStop nearestTram = null;
                PTVStop nearestBus = null;
                for (PTVSearchRecord ptvRecord : nearestPublicTransport) {
                    if (ptvRecord.getResult().getTransport_type().equals("train") && nearestTrain == null) {
                        nearestTrain = ptvRecord.getResult();
                    } else if (ptvRecord.getResult().getTransport_type().equals("tram") && nearestTram == null) {
                        nearestTram = ptvRecord.getResult();
                    } else if (ptvRecord.getResult().getTransport_type().equals("bus") && nearestBus == null) {
                        nearestBus = ptvRecord.getResult();
                    }
                }
                vEvent.setNearestTram(nearestTram);
                vEvent.setNearestTrain(nearestTrain);
                vEvent.setNearestBus(nearestBus);

                eventList.add(vEvent);
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return eventList;
    }

    private static SyndFeed GetRSSFeed(String feedURL) {
        // TODO: FeedFetcherCache can be used to only retrieve new feeds in the future.

        FeedFetcher feedFetcher = new HttpURLFeedFetcher();
        SyndFeed feed = null;
        try {
            feed = feedFetcher.retrieveFeed(new URL(feedURL));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (FetcherException e) {
            e.printStackTrace();
        }

        return feed;
    }
}
