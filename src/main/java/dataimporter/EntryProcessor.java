package dataimporter;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.VEvent;

import java.util.Date;
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
        for(String date : getDates()) {
            VEvent vEvent = new VEvent();

            //vEvent.setEventDate(Date(date));
        }
    }

    private List<String> getDates() {
        // TODO
        return null;
    }

    private String getTitle() {
        return entry.getTitle();
    }

    private List<SyndEntryImpl> getInChildren(String elementName) {

    }
}
