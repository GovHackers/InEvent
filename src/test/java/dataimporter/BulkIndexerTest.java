package dataimporter;


import domain.VEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BulkIndexerTest {

    private BulkIndexer bulkIndexer;
    private VEvent event1;
    private VEvent event2;

    @Before
    public void setUp(){
        bulkIndexer = new BulkIndexer();
        event1 = new VEvent();
        event2 = new VEvent();
        populateEvents();
    }

    @Test
    public void shouldIndexVEventsIntoElasticsearch(){
        List<VEvent> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        bulkIndexer.indexEvents(events);
    }


    private void populateEvents() {
        event1.setId(1);
        event2.setId(2);

        event1.setTitle("Super event 1");
        event2.setTitle("Supre event 2");
    }

}