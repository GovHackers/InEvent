package repository;

import domain.GPSCoords;
import domain.VEvent;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EventRepositoryTest {

    private EventRepository eventRepository;
    private GPSCoords coords;

    @Before
    public void setUp(){
        eventRepository = new EventRepository();
        coords = new GPSCoords();
    }

    /*@Test
    public void itShouldReturnTheFirstEvent(){
        assertThat(eventRepository.getEvents(coords), is(true));
    }*/

}