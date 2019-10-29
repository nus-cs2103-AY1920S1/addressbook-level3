package seedu.address.model.module;

import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Venue)) {
            return false;
        }
        Venue v = (Venue) other;
        if (v == this) {
            return true;
        } else if (v.venue.equals(this.venue)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(venue);
    }
}
