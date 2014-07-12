package domain;

import java.util.Date;
import java.util.List;

/**
 * Created by jrigby on 12/07/2014.
 *
 * Represents a single event occurrence
 */
public class VEvent {

    private int id;
    private String title;
    private String link;
    private String bookingUrl;
    private String bookingAgencyLogo;
    private String description;
    private String category;
    private List<String> tag;
    private Date eventDate;
    private Venue venue;
    private List<String> imageUrl;
    private String contactPhone;
    private String contactEmail;
    private boolean isFree;
    private boolean priceKnown;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
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

    public void setImageUrl(List<String> imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getImageUrl() { return imageUrl; }
}
