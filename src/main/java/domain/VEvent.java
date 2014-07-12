package domain;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import ptvapi.PTVStop;

public class VEvent {

    private long id;
    private String title;
    private String link;
    private String bookingUrl;
    private String bookingAgencyLogo;
    private String description;
    private String category;
    private String ineventCategory;
    private List<String> tag;
    private long eventDate;
    private Venue venue;
    private List<String> imageUrls;
    private String contactPhone;
    private String contactEmail;
    private boolean isFree;
    private boolean priceKnown;
    private double price;
    private String url;
    private String type;
    private PTVStop nearestTrain;
    private PTVStop nearestTram;
    private PTVStop nearestBus;
    private String location;

    public String getIneventCategory() { return ineventCategory; }

    public void setIneventCategory(String ineventCategory) { this.ineventCategory = ineventCategory; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public boolean isPriceKnown() { return priceKnown; }

    public void setPriceKnown(boolean priceKnown) { this.priceKnown = priceKnown; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getBookingUrl() { return bookingUrl; }

    public void setBookingUrl(String bookingUrl) { this.bookingUrl = bookingUrl; }

    public String getBookingAgencyLogo() { return bookingAgencyLogo; }

    public void setBookingAgencyLogo(String bookingAgencyLogo) { this.bookingAgencyLogo = bookingAgencyLogo; }

    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public List<String> getImageUrls() { return imageUrls; }

    public PTVStop getNearestTrain() { return nearestTrain; }

    public void setNearestTrain(PTVStop nearestTrain) { this.nearestTrain = nearestTrain; }

    public PTVStop getNearestTram() { return nearestTram; }

    public void setNearestTram(PTVStop nearestTram) { this.nearestTram = nearestTram; }

    public PTVStop getNearestBus() { return nearestBus; }

    public void setNearestBus(PTVStop nearestBus) { this.nearestBus = nearestBus; }

    public String toJson(){
        // Manually populate the location for ElasticSearch:
        location = venue.getLocation();

        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
