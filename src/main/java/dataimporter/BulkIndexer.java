package dataimporter;

import domain.VEvent;
import factory.ElasticsearchClientFactory;
import org.elasticsearch.client.Client;
import service.ElasticsearchIndexService;

import java.util.List;

import static org.elasticsearch.client.Requests.createIndexRequest;
import static org.elasticsearch.client.Requests.deleteIndexRequest;

public class BulkIndexer {

    private ElasticsearchIndexService indexService;
    private Client client;
    private static String INDEX_NAME = "inevent";


    public BulkIndexer(){
        client = ElasticsearchClientFactory.getInstance();
        indexService = new ElasticsearchIndexService(client);
    }

    public void indexEvents(List<VEvent> events){
        clearIndex();
        events.forEach((event) -> indexService.indexEvent(event));
    }

    public void clearIndex(){
        deleteIndex();
        createIndex();
    }

    private void createIndex() {
        client.admin().indices().create(createIndexRequest(INDEX_NAME)).actionGet();
    }

    private void deleteIndex() {
        client.admin().indices().delete(deleteIndexRequest(INDEX_NAME)).actionGet();
    }
}
