package eventbriteapi;

import java.util.HashSet;

/**
 * Created by jrigby on 13/07/2014.
 */
public class EventbriteResults {
    private Pagination pagination;

    private HashSet<EventbriteEvent> events;

    public HashSet<EventbriteEvent> getEvents() {
        return events;
    }

    public void setEvents(HashSet<EventbriteEvent> events) {
        this.events = events;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public class Pagination {
        private int object_count;
        private int page_number;
        private int page_size;
        private int page_count;

        public int getObject_count() {
            return object_count;
        }

        public void setObject_count(int object_count) {
            this.object_count = object_count;
        }

        public int getPage_number() {
            return page_number;
        }

        public void setPage_number(int page_number) {
            this.page_number = page_number;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }
    }
}
