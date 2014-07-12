package dataimporter;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TagsParser {
    private static LinkedList<String[]> tagsData = new LinkedList<>();

    public TagsParser() {
        parseTagsFile();
        System.out.println("tagsData: " + tagsData.size());
    }

    private void parseTagsFile() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream("tags.csv")));
            while ((line = br.readLine()) != null) {
                tagsData.add(line.split(cvsSplitBy));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getTagLabel(String tag) {
        List<String[]> result = new ArrayList<String[]>();

        for (String[] tagsDataLine : tagsData) {
            if (tagsDataLine[0].contentEquals(tag))
                return tagsDataLine[1];
        }

        // We failed to find the tag, return the original:
        return tag;
    }
}
