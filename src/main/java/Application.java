import static spark.Spark.get;

public class Application {

    public static void main(String[] args){
        get("/api", (request, response) ->  {return "foops"; } );
    }
}
