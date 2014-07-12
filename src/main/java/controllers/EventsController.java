package controllers;

import com.google.gson.Gson;
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

    public String getEvents(){
        Gson gson = new Gson();
        return gson.toJson(repository.getEvents());
    }

    //public String get



}
