package seedu.address.model.inventory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Placeholder javadoc.
 */
public class Inventory {

    private final String name;

    private BooleanProperty isDoneProperty;

    public Inventory(String name, boolean isDone) {
        this.name = name;
        this.isDoneProperty = new SimpleBooleanProperty(isDone);
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

    public String getName() {
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
