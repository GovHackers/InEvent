package dataimporter;

import domain.VEvent;
import factory.ElasticsearchClientFactory;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.indices.IndexMissingException;
import service.ElasticsearchIndexService;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.client.Requests.createIndexRequest;
import static org.elasticsearch.client.Requests.deleteIndexRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class BulkIndexer {

    private ElasticsearchIndexService indexService;
    private Client client;
    private static String INDEX_NAME = "inevent";
    private static String DOCUMENT_TYPE = "event";


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

       // CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(INDEX_NAME);
        XContentBuilder mappingBuilder = null;

        // TODO: Move location back into venue model
        try {
            mappingBuilder = jsonBuilder().startObject().startObject(DOCUMENT_TYPE).startObject("properties")
                    .startObject("eventDate")
                        .field("type", "date")
                       // .field("format", "basic_date_time_no_millis")
                        .field("store", "yes")
                    .endObject()
                    .startObject("location")
                        .field("type", "geo_point")
                        .field("store", "yes")

                    .endObject()
                .endObject()
                .endObject()
                .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // createIndexRequestBuilder.addMapping(DOCUMENT_TYPE, mappingBuilder);
       // createIndexRequestBuilder.execute().actionGet();

        client.admin().indices().create(createIndexRequest(INDEX_NAME).mapping(DOCUMENT_TYPE, mappingBuilder)).actionGet();
        System.out.println("Index mapping created");
        // client.admin().indices().create(createIndexRequest(INDEX_NAME)).actionGet();
    }

    private void deleteIndex() {
        try {
            client.admin().indices().delete(deleteIndexRequest(INDEX_NAME)).actionGet();
        }
        catch (IndexMissingException e) {
            System.out.println("Index has already beed deleteed");
        }
    }
}
