package dataimporter;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.VEvent;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        for(Date date : getDates()) {
            VEvent vEvent = new VEvent();

            vEvent.setEventDate(date);
        }
    }

    private List<Date> getDates() {
        System.out.println("-----------");
        List<String> dates = new LinkedList<String>();
        for (Element e : (ArrayList<Element>)entry.getForeignMarkup()) {
            if (e.getName().equals("myEvents:eventDate")) {
                dates.add(e.getText());
                System.out.println(e.getText());
            }
        }
        return null;
    }

    private String getTitle() {
        return entry.getTitle();
    }

    private List<SyndEntryImpl> getInChildren(String elementName) {
        return null;
    }
}
