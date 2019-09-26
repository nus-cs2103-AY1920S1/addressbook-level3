package seedu.address.model.person.schedule;

/**
 * Venue object.
 */
public class Venue {
    private String venue;
    private String coordinates;

    public Venue(String venue) {
        this.venue = venue;
        this.coordinates = null;
    }

    public String toString() {
        return venue;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
