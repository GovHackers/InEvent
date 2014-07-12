package dataimporter;

import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * Created by jrigby on 12/07/2014.
 */
public class EntryProcessor {

    private SyndEntryImpl entry;

    public EntryProcessor(SyndEntryImpl eventRSSEntry) {
        entry = eventRSSEntry;
    }

    public String getTitle() {
        return entry.getTitle();
    }
}
