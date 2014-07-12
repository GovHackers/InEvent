package ptvapi;

/**
 * Created by jrigby on 12/07/2014.
 */
public class PTVSearchRecord implements Comparable<PTVSearchRecord> {
    private String type;
    private PTVStop result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PTVStop getResult() {
        return result;
    }

    public void setResult(PTVStop result) {
        this.result = result;
    }

    @Override
    public int compareTo(PTVSearchRecord o) {
        return result.getDistance().compareTo(o.getResult().getDistance());
    }
}
