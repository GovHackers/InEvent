package eventbriteapi;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jrigby on 12/07/2014.
 */
public class EventbriteApi {
    private String oAuthToken;

    public EventbriteApi(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    private String readFromUrl(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Wget/1.14 (darwin12.3.0)");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        StringBuilder jsonResult = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            jsonResult.append(inputLine);
        in.close();

        return jsonResult.toString();
    }

    public Set<EventbriteEvent> getBasicEventsJson(String searchAddress, String searchRadius, boolean popular) {
        return getBasicEventsJson(searchAddress, searchRadius, popular, 1);
    }

    private Set<EventbriteEvent> getBasicEventsJson(String searchAddress, String searchRadius, boolean popular, int page) {
        String url = "";
        try {
            url = "https://www.eventbriteapi.com/v3/events/search/?location.address="+
                    URLEncoder.encode(searchAddress, "UTF-8")+
                    "&location.within="+URLEncoder.encode(searchRadius, "UTF-8")+
                    "&popular="+String.valueOf(popular)+
                    "&page="+String.valueOf(page)+
                    "&token="+this.oAuthToken;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        String jsonData = "";
        try {
            InputStream inputStream = httpclient.execute(httpget).getEntity().getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");
            jsonData = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        EventbriteResults currentResults = gson.fromJson(jsonData, EventbriteResults.class);
        Set<EventbriteEvent> allEvents = new HashSet<EventbriteEvent>();
        allEvents.addAll(currentResults.getEvents());
        System.out.println(currentResults.getPagination().getPage_number()+"of"+currentResults.getPagination().getPage_count());
        if (currentResults.getPagination().getPage_number() < currentResults.getPagination().getPage_count()) {
            allEvents.addAll(getBasicEventsJson(searchAddress, searchRadius, popular, page+1));
            return allEvents;
        } else {
            return allEvents;
        }
    }

    public static void main(String[] args) {
        EventbriteApi api = new EventbriteApi("API-KEY-GOES-HERE");
        Set<EventbriteEvent> results = api.getBasicEventsJson("melbourne", "30km", true);

        for (EventbriteEvent e : results) {
            System.out.println(e.name.text);
        }
    }
}
