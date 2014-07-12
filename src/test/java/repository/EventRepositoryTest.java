package repository;

import domain.VEvent;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EventRepositoryTest {

    private EventRepository eventRepository;

    @Before
    public void setUp(){
        eventRepository = new EventRepository();
    }

    @Test
    public void itShouldReturnTheFirstEvent(){
        assertThat(eventRepository.getEvents(), is(true));
    }

}