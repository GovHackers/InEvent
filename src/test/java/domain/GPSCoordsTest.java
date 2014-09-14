package domain;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GPSCoordsTest {

    GPSCoords coords1;
    GPSCoords coords2;


    @Before
    public void setUp() throws Exception {
        coords1 = new GPSCoords(38.898556, -77.037852);
        coords2 = new GPSCoords(38.897147, -77.043934);
    }

    @Test
    public void itCalculatesTheDistanceFromAnotherPointInKilometers() {
        assertThat(coords1.distanceFrom(coords2), is(0.5491557912038083));
    }
}