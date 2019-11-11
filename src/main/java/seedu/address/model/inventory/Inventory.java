package seedu.address.model.inventory;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Placeholder javadoc.
 */
public class Inventory {

    private final Name name;

    private BooleanProperty isDoneProperty;

    private boolean isRemovable;

    private int eventInstances;

    public Inventory(Name name, boolean isDone, int eventInstances) {

        requireAllNonNull(name, isDone);

        this.name = name;

        this.isDoneProperty = new SimpleBooleanProperty(isDone);

        //this.isRemovable = isRemovable;

        this.eventInstances = eventInstances;

    }

    public void setIsDone (Boolean boo) {
        this.isDoneProperty.set(boo);
    }

    public BooleanProperty getIsDoneProperty() {
        return this.isDoneProperty;
    }

    public boolean getIsDone() {
        return this.isDoneProperty.get();
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns true if both {@link Trip} contain the same booking and their to and from time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameInventory(Inventory otherInventory) {
        if (otherInventory == this) {
            return true;
        } else {
            return otherInventory != null
                    && otherInventory.getName().equals(getName());
        }
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof Inventory)) {
            return false;
        }

        Inventory otherInventory = (Inventory) other;
        return otherInventory.getName().equals(getName());
    }

    @Override
    public String toString() {
        return name.toString();
    }

    public void setIsRemovable (Boolean boo) {
        this.isRemovable = boo;
    }

    public boolean getIsRemovable() {
        return this.isRemovable;
    }

    public int getEventInstances () {
        return this.eventInstances;
    }

    public void setEventInstances (int eventInstances) {
        this.eventInstances = eventInstances;
    }

    /*
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name.toString())
                .append(" From: ")
                .append(ParserDateUtil.getDisplayTime(startDate))
                .append(" To: ")
                .append(ParserDateUtil.getDisplayTime(endDate))
                .append(" Destination: ")
                .append(destination.toString())
                .append(" Total Budget: ")
                .append(totalBudget.toString());

        return builder.toString();
    }*/
}
