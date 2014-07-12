package controllers;

import com.google.gson.Gson;
import domain.GPSCoords;
import factory.ElasticsearchClientFactory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import repository.EventRepository;
import spark.Request;
import spark.Response;

public class EventsController {

    private EventRepository repository;

    private Request request;
    private Response response;

    public EventsController(Request request, Response response){
        this.request = request;
        this.response = response;
        this.repository = new EventRepository();
    }

    public String getEvents() {
        Gson gson = new Gson();
        GPSCoords coords = new GPSCoords();

        String lat = request.queryParams("lat");
        String lon = request.queryParams("lon");

        if ( lat == null || lon == null) {
            throw new ManditoryQueryParameterException();
        }

        coords.setLat(Double.valueOf(lat));
        coords.setLon(Double.valueOf(lon));

        if(request.queryParams("query_set") == null ) {
            return gson.toJson(repository.getEvents(coords));
        }
        Integer querySet = Integer.valueOf(request.queryParams("query_set"));
        return gson.toJson(repository.getEvents(coords, querySet));
    }

}
