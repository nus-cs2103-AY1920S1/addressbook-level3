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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Venue)){
            return false;
        } else if (((Venue) obj).getVenue().equals(venue)){
            return true;
        } else {
            return false;
        }
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
