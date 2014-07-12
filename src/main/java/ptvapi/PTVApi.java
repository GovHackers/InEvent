package ptvapi;

import com.google.gson.Gson;
import domain.GPSCoords;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by jrigby on 12/07/2014.
 *
 * Interface to the PTV API
 * Requires a configuration file in the class path called "ptv-keys.properties"
 * containing the developer ID, "devid", and the api key, "api-key".
 */
public class PTVApi {

    private Configuration ptvKeyConfigFile;

    private RequestSignature reqSignature;

    public PTVApi() {
        // Load configuration
        try {
            ptvKeyConfigFile = new PropertiesConfiguration("ptv-keys.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

        reqSignature = new RequestSignature(ptvKeyConfigFile.getInt("devid"), ptvKeyConfigFile.getString("api-key"));
    }

    private String getReqUrl(String uri) {
        return reqSignature.generateRequestURL(uri);
    }

    private String readFromUrl(String url) throws IOException {
        URL ptvUrl = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ptvUrl.openStream()));

        StringBuilder jsonResult = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            jsonResult.append(inputLine);
        in.close();

        return jsonResult.toString();
    }

    public PTVResultsSet getNearestTransport(GPSCoords location) {
        Gson gson = new Gson();
        PTVResultsSet results = new PTVResultsSet();
        try {
            results = gson.fromJson(readFromUrl(getReqUrl("/v2/nearme/latitude/" + String.valueOf(location.getLat()) + "/longitude/" + String.valueOf(location.getLon()))), PTVResultsSet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    // Test case
    public static void main(String[] args) {
        PTVApi api = new PTVApi();
        GPSCoords coords = new GPSCoords(-37.82392124423254, 144.9462017431463);
        for (PTVSearchRecord record : api.getNearestTransport(coords)) {
            System.out.println(record.getResult().getLocation_name());
        }
    }

}
