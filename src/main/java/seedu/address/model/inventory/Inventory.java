package seedu.address.model.inventory;

/**
 * Placeholder javadoc.
 */
public class Inventory {
    public final String name;

    public Inventory(String name) {
        this.name = name;
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
