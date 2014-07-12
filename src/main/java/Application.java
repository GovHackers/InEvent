import controllers.EventsController;
import controllers.ManditoryQueryParameterException;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class Application {

    public static final String GET_RELEVANT_EVENTS = "/api/events/get_relevant";

    public static void main(String[] args){
        
        staticFileLocation("/public");

        get(GET_RELEVANT_EVENTS, "application/json", (request, response) -> {
            return new EventsController(request, response).getEvents();
        });

        after(GET_RELEVANT_EVENTS, (request, response) -> {
            response.type("application/json");
        });


    }
}
