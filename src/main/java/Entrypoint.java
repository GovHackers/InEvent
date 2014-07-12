import dataimporter.Importer;

/**
 * Created by jrigby on 12/07/2014.
 */
public class Entrypoint {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("populate")) {
            System.out.println("Populating events database...");
            Importer.main(args);
            System.out.println("Population complete.");
        } else {
            System.out.println("=== InEvent server ===");
            Application.main(args);
        }
    }
}
