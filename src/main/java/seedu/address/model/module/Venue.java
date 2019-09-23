package seedu.address.model.module;

/**
 * Venue of the module
 */
public class Venue {
    private String venue;

    public Venue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return venue;
    }
}
