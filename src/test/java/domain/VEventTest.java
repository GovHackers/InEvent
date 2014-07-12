package domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class VEventTest {

    private VEvent event;

    @Before
    public void setUp() throws Exception {
        event = new VEvent();
        event.setId(1);
        event.setTitle("my event title");
    }

    @Test
    public void itShouldHaveAJsonRepresentationOfTheEvent(){
        assertThat(event.toJson(), is(String.class));
    }
}