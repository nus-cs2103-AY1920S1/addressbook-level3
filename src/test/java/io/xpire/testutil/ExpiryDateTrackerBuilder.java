package io.xpire.testutil;

import io.xpire.model.Xpire;
import io.xpire.model.item.XpireItem;

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

    /**
     * Adds a new {@code XpireItem} to the {@code ExpiryDateTracker} that we are building.
     */
    public ExpiryDateTrackerBuilder withItem(XpireItem xpireItem) {
        xpire.addItem(xpireItem);
        return this;
    }

    public Xpire build() {
        return xpire;
    }
}
