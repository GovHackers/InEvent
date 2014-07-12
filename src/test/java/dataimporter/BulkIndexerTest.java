package dataimporter;


import domain.VEvent;
import domain.Venue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BulkIndexerTest {

    private BulkIndexer bulkIndexer;
    private VEvent event1;
    private VEvent event2;

    private Venue venue1;
    private Venue venue2;

    @Before
    public void setUp(){
        bulkIndexer = new BulkIndexer();
        event1 = new VEvent();
        event2 = new VEvent();
        venue1 = new Venue();
        venue2 = new Venue();
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

        venue1.setLat(80.12345);
        venue1.setLon(140.5678);

        venue2.setLat(70.12345);
        venue2.setLon(120.4567);

        event1.setVenue(venue1);
        event2.setVenue(venue2);
    }

}