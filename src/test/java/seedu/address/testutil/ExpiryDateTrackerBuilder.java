package seedu.address.testutil;

import seedu.address.model.ExpiryDateTracker;
import seedu.address.model.item.Item;

/**
 * A utility class to help with building ExpiryDateTracker objects.
 * Example usage: <br>
 *     {@code ExpiryDateTracker edt = new ExpiryDateTrackerBuilder().withItem("Fruit Jam").build();}
 */
public class ExpiryDateTrackerBuilder {

    private ExpiryDateTracker expiryDateTracker;

    public ExpiryDateTrackerBuilder() {
        expiryDateTracker = new ExpiryDateTracker();
    }

    public ExpiryDateTrackerBuilder(ExpiryDateTracker expiryDateTracker) {
        this.expiryDateTracker = expiryDateTracker;
    }

    /**
     * Adds a new {@code Item} to the {@code ExpiryDateTracker} that we are building.
     */
    public ExpiryDateTrackerBuilder withItem(Item item) {
        expiryDateTracker.addItem(item);
        return this;
    }

    public ExpiryDateTracker build() {
        return expiryDateTracker;
    }
}
