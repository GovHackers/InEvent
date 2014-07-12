package dataimporter;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

import org.jdom.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    public static void main(String[] args) {

        SyndFeed f = GetRSSFeed("http://www.eventsvictoria.com/distributionservice/rss.xml");
        for (SyndEntryImpl entry : (List<SyndEntryImpl>)f.getEntries()) {
            for (Element element : (ArrayList<Element>)entry.getForeignMarkup()) {
                System.out.println("NAME: "+element.getName());
                System.out.println("VALUE: "+element.getValue());
            }
        }
    }

    void ImportRSSFeed(String feedURL) {

    }

    static SyndFeed GetRSSFeed(String feedURL) {
        // TODO: FeedFetcherCache can be used to only retrieve new feeds in the future.

        FeedFetcher feedFetcher = new HttpURLFeedFetcher();
        SyndFeed feed = null;
        try {
            feed = feedFetcher.retrieveFeed(new URL(feedURL));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (FetcherException e) {
            e.printStackTrace();
        }

        return feed;
    }
}
