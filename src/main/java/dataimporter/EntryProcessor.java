package dataimporter;

import com.google.gson.Gson;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.VEvent;
import domain.Venue;
import org.jdom.Element;

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

            // Price data not available yet in the data set
            event.setPrice(0.0);
            event.setPriceKnown(false);
        }

    }

    public List<VEvent> getVEvents() {
        return vEvents;
    }

    private void generateEventsFromDates() {
        vEvents = new LinkedList<VEvent>();
        for(Date date : getDates()) {
            VEvent vEvent = new VEvent();

            vEvent.setEventDate(date);

            vEvents.add(vEvent);
        }
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
            tags.add(tag);
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

    private List<Date> getDates() {
        List<Date> dates = new LinkedList<Date>();
        for (String dateString : getItemValuesInEntry("eventDate")) {
                try {
                    Date date = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(dateString);
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
