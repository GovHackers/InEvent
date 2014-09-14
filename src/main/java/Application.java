import controllers.EventsController;
import controllers.ManditoryQueryParameterException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class Application {

    public static final String GET_RELEVANT_EVENTS = "/api/events/get_relevant";
    public static final String INEVENT_HTTP_CONTEXT = "inevent-http.xml";

    public static void main(String[] args){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(INEVENT_HTTP_CONTEXT);
        Application application = applicationContext.getBean("application", Application.class);

        application.initialiseRoutes();
    }

    private void initialiseRoutes() {
        staticFileLocation("/public");

        get(GET_RELEVANT_EVENTS, "application/json", (request, response) -> {
            return new EventsController(request, response).getEvents();
        });

        after(GET_RELEVANT_EVENTS, (request, response) -> {
            response.type("application/json");
        });
    }
}
