package repository;

import domain.GPSCoords;
import factory.ElasticsearchClientFactory;
import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.gauss.GaussDecayFunctionBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventRepository {

    private Client client;

    private final static String INDEX_NAME = "inevent";
    private final static String TYPE = "event";
    private final static String EVENT_DATE_SCALE = "3d";
    private final static String LOCATION_SCALE = "10km";

    public EventRepository(){
        this.client = new ElasticsearchClientFactory().getInstance();
    }

    public List<Map<String, Object>> getEvents(GPSCoords coords, int setFrom){
        List<Map<String, Object>> events = new ArrayList<>();

        GaussDecayFunctionBuilder eventDateFunction = ScoreFunctionBuilders.gaussDecayFunction("eventDate", EVENT_DATE_SCALE);
        GaussDecayFunctionBuilder locationFunction = ScoreFunctionBuilders.gaussDecayFunction("location", coords.getLocation(), LOCATION_SCALE);

        FunctionScoreQueryBuilder queryBuilder = QueryBuilders.functionScoreQuery()
                .add(eventDateFunction)
                .add(locationFunction)
                .scoreMode("multiply")
                .boostMode("multiply")
                .boost(1000);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE)
                .setFrom(setFrom)
                .setSize(1)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();


        SearchHit[] results = response.getHits().getHits();
        for(SearchHit result : results){
            events.add(result.getSource());
        }
        return events;

    }

    public List<Map<String, Object>> getEvents(GPSCoords coords){
        return getEvents(coords, 0);
    }

    /*

    {  "query": {
        "function_score": {
            "functions": [
            {
                "gauss": {
                "eventDate": {
                    "scale": "3d"
                }
            }
            },
            {
                "gauss": {
                "location": {
                    "origin": "-37.6430871, 142.0255043",
                            "scale": "10km"
                }
            }
            }
            ],
            "score_mode": "multiply",
                    "boost_mode": "multiply",
                    "boost": "100000"
        }
    }
    }
    */

}
