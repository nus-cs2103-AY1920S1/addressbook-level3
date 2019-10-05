package io.xpire.testutil;

import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * A utility class to help with building ExpiryDateTracker objects.
 * Example usage: <br>
 *     {@code ExpiryDateTracker edt = new ExpiryDateTrackerBuilder().withItem("Fruit Jam").build();}
 */
public class ExpiryDateTrackerBuilder {

    private Xpire xpire;

    public ExpiryDateTrackerBuilder() {
        xpire = new Xpire();
    }

    public ExpiryDateTrackerBuilder(Xpire xpire) {
        this.xpire = xpire;
    }

    /**
     * Adds a new {@code Item} to the {@code ExpiryDateTracker} that we are building.
     */
    public ExpiryDateTrackerBuilder withItem(Item item) {
        xpire.addItem(item);
        return this;
    }

    public Xpire build() {
        return xpire;
    }
}
