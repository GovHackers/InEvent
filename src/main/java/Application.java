import controllers.EventsController;

import static spark.Spark.get;

public class Application {

    public static void main(String[] args){

        EventsController eventsController;



        get("/api/events/get_relevant", (request, response) ->  {
            return new EventsController(request, response).getEvents(); }
        );
    }
}
