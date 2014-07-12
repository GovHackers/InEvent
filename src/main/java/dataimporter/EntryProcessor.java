package dataimporter;

import com.sun.syndication.feed.synd.SyndEntryImpl;

public class EntryProcessor {

    private SyndEntryImpl entry;

    public EntryProcessor(SyndEntryImpl eventRSSEntry) {
        entry = eventRSSEntry;
    }

    public String getTitle() {
        return entry.getTitle();
    }
}
