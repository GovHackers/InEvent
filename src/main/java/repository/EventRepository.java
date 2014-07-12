package repository;

import factory.ElasticsearchClientFactory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventRepository {

    private Client client;

    private final static String INDEX_NAME = "inevent";
    private final static String TYPE = "event";

    public EventRepository(){
        this.client = new ElasticsearchClientFactory().getInstance();
    }

    public List<Map<String, Object>> getEvents(int setFrom){
        List<Map<String, Object>> events = new ArrayList<>();

        SearchResponse response = client.prepareSearch(INDEX_NAME).setTypes(TYPE).setFrom(setFrom).setSize(1).execute().actionGet();
        SearchHit[] results = response.getHits().getHits();
        for(SearchHit result : results){
            events.add(result.getSource());
        }
        return events;

    }

    public List<Map<String, Object>> getEvents(){
        return getEvents(0);
    }

}
