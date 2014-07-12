package dataimporter;

import com.google.gson.Gson;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.VEvent;
import domain.Venue;
import org.hamcrest.generator.qdox.model.util.TagParser;
import org.jdom.Element;
import ptvapi.PTVApi;
import ptvapi.PTVResultsSet;
import ptvapi.PTVSearchRecord;
import ptvapi.PTVStop;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntryProcessor {
    private SyndEntryImpl entry;
    private List<VEvent> vEvents;

    public EntryProcessor(SyndEntryImpl eventRSSEntry) {
        entry = eventRSSEntry;
        generateEventsFromDates();

        for (VEvent event : vEvents) {
            // Add event properties here
            event.setId(getId());
            event.setTitle(getTitle());
            event.setLink(getLink());
            event.setDescription(getDescription());
            event.setCategory(getCategory());
            event.setTag(getTags());
            event.setVenue(getVenue());
            event.setImageUrls(getImageUrls());
            event.setContactPhone(getContactPhone());
            event.setContactEmail(getContactEmail());
            event.setUrl(getUrl());
            event.setFree(getFreeEntry());
            event.setType(getType());
            event.setIneventCategory(getIneventCategory());

            // Price data not available yet in the data set
            event.setPrice(getPrice());
            event.setPriceKnown(getPriceKnown());
        }

        populateTransportOptions();
    }

    private String getIneventCategory() {
        return TagsParser.getIneventCategory(getItemValuesInEntry("tags"));
    }

    private void populateTransportOptions() {
        VEvent event = vEvents.get(0);
        PTVApi ptvApi = new PTVApi();
        PTVResultsSet nearestPublicTransport = ptvApi.getNearestTransport(event.getVenue());
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

        for (VEvent event1 : vEvents) {
            if (nearestBus != null) {
                event1.setNearestBus(nearestBus);
            }
            if (nearestTrain != null) {
                event1.setNearestTrain(nearestTrain);
            }
            if (nearestTram != null) {
                event1.setNearestTram(nearestTram);
            }
        }
    }

    public List<VEvent> getVEvents() {
        return vEvents;
    }

    private void generateEventsFromDates() {
        vEvents = new LinkedList<VEvent>();
        for(Long date : getDates()) {
            VEvent vEvent = new VEvent();

            vEvent.setEventDate(date);

            vEvents.add(vEvent);
        }
    }

    private String getUrl() {
        return getItemValueInEntry("url");
    }

    private boolean getPriceKnown() {
        if(getFreeEntry() == false)
            return false;
        else
            return true;
    }

    private double getPrice() {
        // TODO - Currently no data for this.
        return 0.0;
    }

    private boolean getFreeEntry() {
        return Boolean.valueOf(getItemValueInEntry("freeEntry"));
    }

    private String getType() {
        return getItemValueInEntry("type");
    }

    private String getContactEmail() {
        return getItemValueInEntry("email");
    }

    private LinkedList<String> getImageUrls() {
        List<Element> multimediaElements = getItemElementsInEntry("multimedia");

        LinkedList<String> imageUrls = new LinkedList<>();
        for(Element multimediaElement : multimediaElements) {
            List<Element> images = getItemElementsInEntry("image");
            for (Element image : images) {
                imageUrls.add(image.getChild("serverPath").getValue());
            }
        }

        //multimediaElements.forEach((image) ->
        //        imageUrls.add(image.getChild("serverPath").getValue()));

        return imageUrls;
    }

    private Venue getVenue() {
        Venue venue = new Venue();

        // get venue name
        Element venueElement = getItemElementsInEntry("venue").get(0);
        venue.setName(venueElement.getAttribute("name").getValue());
        venue.setAddressLine1(venueElement.getChild("address").getChild("address1").getValue());
        venue.setAddressLine2(venueElement.getChild("address").getChild("address2").getValue());
        venue.setAddressLine3(venueElement.getChild("address").getChild("address3").getValue());
        venue.setLat(Double.valueOf(venueElement.getChild("address").getChild("latitude").getValue()));
        venue.setLon(Double.valueOf(venueElement.getChild("address").getChild("longitude").getValue()));

        venue.setPostcode(venueElement.getChild("postcode").getValue());
        venue.setSuburb(venueElement.getChild("city").getValue());
        return venue;
    }

    private List<String> getTags() {
        List<String> tags = new LinkedList<String>();
        for (String tag : getItemValuesInEntry("tags")) {
            tags.add(TagsParser.getTagLabel(tag));
        }
        return tags;
    }

    private String getCategory() {
        return ((SyndCategoryImpl)entry.getCategories().get(0)).getName();
    }

    private String getDescription() {
        return entry.getDescription().getValue();
    }

    private String getLink() {
        return entry.getLink();
    }

    private int getId() {
        return Integer.valueOf(entry.getUri());
    }

    private String getContactPhone() {
        return getItemValuesInEntry("phone").get(0);
    }

    private List<Long> getDates() {
        List<Long> dates = new LinkedList<Long>();
        for (String dateString : getItemValuesInEntry("eventDate")) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+10"));

                    long date = dateFormat.parse(dateString).getTime();// / 1000; // Unit time is in seconds
                    dates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
        return dates;
    }

    private String getTitle() {
        return entry.getTitle();
    }

    private String getItemValueInEntry(String itemName) {
        return getItemValuesInEntry(itemName).get(0);
    }

    private List<String> getItemValuesInEntry(String elementName) {
        List<String> elements = new LinkedList<String>();
        for (Element element : (ArrayList<Element>)entry.getForeignMarkup()) {
            if (element.getName().equals(elementName)) {
                elements.add(element.getText());
            }
        }
        return elements;
    }

    private List<Element> getItemElementsInEntry(String elementName) {
        List<Element> elements = new LinkedList<Element>();
        for (Element element : (ArrayList<Element>)entry.getForeignMarkup()) {
            if (element.getName().equals(elementName)) {
                elements.add(element);
            }
        }
        return elements;
    }
}
