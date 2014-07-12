package domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ExclusionFilterTest {

    @Test
    public void itShouldParseASingleFilter(){
        ExclusionFilter filter = new ExclusionFilter("cat a");
        assertThat(filter.getExclusions(), contains("cat a"));
    }

    @Test
    public void itShouldParseMultipleFilters(){
        ExclusionFilter filter = new ExclusionFilter("cat a,cat b");
        assertThat(filter.getExclusions(), contains("cat a"));
        assertThat(filter.getExclusions(), contains("cat b"));
    }

}