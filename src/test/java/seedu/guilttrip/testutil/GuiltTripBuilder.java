package seedu.guilttrip.testutil;

import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.entry.Entry;

/**
 * A utility class to help with building GuiltTrip objects.
 * Example usage: <br>
 * {@code GuiltTrip gt = new GuiltTripBuilder().withEntry(<entry object>).build();}
 */
public class GuiltTripBuilder {

    private GuiltTrip guiltTrip;

    public GuiltTripBuilder() {
        guiltTrip = new GuiltTrip(true);
    }

    public GuiltTripBuilder(GuiltTrip guiltTrip) {
        this.guiltTrip = guiltTrip;
    }

    /**
     * Adds a new {@code Entry} to the {@code GuiltTrip} that we are building.
     */
    public GuiltTripBuilder withEntry(Entry entry) {
        guiltTrip.addEntry(entry);
        return this;
    }

    public GuiltTrip build() {
        return guiltTrip;
    }
}
