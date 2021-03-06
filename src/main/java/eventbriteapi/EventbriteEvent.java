package eventbriteapi;

import domain.GPSCoords;

/**
 * Created by jrigby on 13/07/2014.
 *
 * TODO: Use getters and setters; make pretty.
 */
public class EventbriteEvent {

    public long id;
    public String resource_uri;
    public TextHtml name;
    public TextHtml description;
    public StartEndTime start;
    public StartEndTime end;
    public Category category;
    public Format format;
    public Venue venue;
    public String logo_url;

    public GPSCoords getCoords() {
        GPSCoords coords = new GPSCoords();
        coords.setLat(venue.latitude);
        coords.setLon(venue.longitude);
        return coords;
    }

    public class TextHtml {
        public String text;
        public String html;
    }

    public class StartEndTime {
        public String timezone;
        public String local;
        public String utc;
    }

    public class Venue {
        public Address address;
        public double latitude;
        public double longitude;
        public String name;
        public class Address {
            public String city;
            public String address_1;
        }
    }

    public class Category {
        public int id;
        public String resource_uri;
        public String name;
        public String name_localized;
        public String short_name;
    }
    public class Format {
        public int id;
        public String resource_uri;
        public String name;
        public String short_name;
    }
}
