package service;

import domain.VEvent;
import org.elasticsearch.client.Client;

public class ElasticsearchIndexService implements IndexService {

    public static final String INDEX_NAME = "inevent";
    public static final String TYPE = "event";
    private Client client;

    public ElasticsearchIndexService(Client client){
        this.client = client;
    }

    @Override
    public void indexEvent(VEvent event) {
        client.prepareIndex(INDEX_NAME, TYPE).setSource(event.toJson()).execute().actionGet();
    }
}
