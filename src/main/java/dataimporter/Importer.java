package dataimporter;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

import org.jdom.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import domain.VEvent;

public class Importer {

    public static void main(String[] args) {
        // Initialize and parse the tags file:
        TagsParser tagsParser = new TagsParser();

        //List<VEvent> listOfEvents = getVEventsFromRSS("http://10.106.100.227:8080/GovHack/InEvent/eventsrss.xml");
        List<VEvent> listOfEvents = getVEventsFromRSS("http://www.eventsvictoria.com/distributionservice/rss.xml");

        BulkIndexer bulkIndexer = new BulkIndexer();
        bulkIndexer.indexEvents(listOfEvents);
    }

    private static List<VEvent> getVEventsFromRSS(String rssURL) {
        SyndFeed f = GetRSSFeed(rssURL);

        List<VEvent> eventList = new LinkedList<VEvent>();

        for (SyndEntryImpl entry : (List<SyndEntryImpl>)f.getEntries()) {
            eventList.addAll(new EntryProcessor(entry).getVEvents());
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
