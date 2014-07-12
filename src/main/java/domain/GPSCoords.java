package domain;

/**
 * Created by jrigby on 12/07/2014.
 *
 * GPS coordinates
 */
public class GPSCoords {
    private double lat;
    private double lon;

    public GPSCoords(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public GPSCoords() {
        lat = 0;
        lon = 0;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Calculates the distance between two points
     * @param point
     * @return
     */
    public double distanceFrom(GPSCoords point) {
        double R = 6371; // km
        double dLat = deg2rad((point.getLat()-this.getLat()));
        double dLon = deg2rad((point.getLat()-this.getLat()));
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(this.getLat())) * Math.cos(deg2rad(point.getLat())) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    /**
     * Degrees to radians
     * @param deg degrees
     * @return radians
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
}
