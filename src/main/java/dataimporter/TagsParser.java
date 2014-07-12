package dataimporter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class TagsParser {
    private static LinkedList<String[]> tagsData = new LinkedList<>();

    public TagsParser() {
        //parseTypesFile();
        parseTagsFile();
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

    /*
    private void parseTypesFile() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream("types.csv")));
            while ((line = br.readLine()) != null) {
                typesData.add(line.split(cvsSplitBy));
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
    */

    public static String getIneventCategory(List<String> tags) {
        String ineventCategory = "";
        for(String tag : tags) {
            for (String[] tagsDataLine : tagsData) {
                if (tagsDataLine[0].contentEquals(tag)) {
                    ineventCategory = tagsDataLine[2];
                    break;
                }
            }
        }

        return ineventCategory;
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

    public static LinkedList<String> getAllTags() {
        LinkedList<String> tagLabels = new LinkedList<>();

        for (String[] tagsDataLine : tagsData) {
            tagLabels.add(tagsDataLine[1]);
        }

        return tagLabels;
    }

    /*
    public static String getTypeLabel(String type) {
        List<String[]> result = new ArrayList<String[]>();

        for (String[] typeDataLine : typesData) {
            if (typeDataLine[0].contentEquals(type))
                return typeDataLine[1];
        }

        // We failed to find the tag, return the original:
        return type;
    }

    public static LinkedList<String> getAllTypes() {
        LinkedList<String> typeLabels = new LinkedList<>();

        for (String[] typesDataLine : typesData) {
            typeLabels.add(typesDataLine[1]);
        }

        return typeLabels;
    }
    */
}
