package domain;

import java.net.URL;
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
    private URL link;
    private URL bookingUrl;
    private URL bookingAgencyLogo;
    private String description;
    private String category;
    private List<String> tag;
    private Date eventDate;
    private Venue venue;
    private List<URL> imageUrl;
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

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
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

    public URL getBookingUrl() { return bookingUrl; }

    public void setBookingUrl(URL bookingUrl) { this.bookingUrl = bookingUrl; }

    public URL getBookingAgencyLogo() { return bookingAgencyLogo; }

    public void setBookingAgencyLogo(URL bookingAgencyLogo) { this.bookingAgencyLogo = bookingAgencyLogo; }

    public void setImageUrl(List<URL> imageUrl) { this.imageUrl = imageUrl; }

    public List<URL> getImageUrl() { return imageUrl; }
}
