package factory;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class ElasticsearchClientFactory {

    private static Client client;
    private static final String CLUSTER_NAME =  "elasticsearch";

    public static Client getInstance() {
        if(client == null) {
            Node node = nodeBuilder().clusterName(CLUSTER_NAME).node();
            client = node.client();
        }
        return client;
    }

}
