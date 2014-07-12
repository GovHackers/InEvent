package dataimporter;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.VEvent;
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

    private List<Date> getDates() {
        List<Date> dates = new LinkedList<Date>();
        for (Element element : (ArrayList<Element>)entry.getForeignMarkup()) {
            if (element.getName().equals("eventDate")) {
                String dateString = element.getText();
                try {
                    Date date = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(dateString);
                    dates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return dates;
    }

    private String getTitle() {
        return entry.getTitle();
    }

    private List<SyndEntryImpl> getInChildren(String elementName) {
        return null;
    }
}
