package seedu.pluswork.model.inventory;

import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//import seedu.address.model.task.Task;

/**
 * Represents a Inventory in ProjectDashBoard.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Inventory {
    // Identity fields
    //private final Task task;
    private final InvName name;
    /*private final memIndex memID;*/
    private final Price price;


    /**
     * Every field must be present and not null.
     */


    public Inventory(/*Task task,*/ InvName name, /*,memIndex memID, */Price price) {
        requireAllNonNull(/*task,*/ name, price);
        //this.task = task;
        this.name = name;
        /*this.memID = memID*/
        this.price = price;
    }

    public Inventory(/*Task task,*/ InvName name/*,memIndex memID, */) {
        requireAllNonNull(/*task,*/ name);
        //this.task = task;
        this.name = name;
        /*this.memID = memID*/
        this.price = new Price(0);
    }

    public InvName getName() {
        return name;
    }

    //public Task getTask() {
    //    return task;
    //}

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameInventory(Inventory otherInv) {
        if (otherInv == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return otherInv != null
                && otherInv.getName().equals(getName())
                && otherInv.getPrice().equals(getPrice())
                /*&& otherInv.getTask().equals(getTask())*/;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Inventory)) {
            return false;
        }

        Inventory otherInv = (Inventory) other;
        return otherInv.getName().equals(getName())
                /*&& otherInv.getTask().equals(getTask())*/
                && otherInv.getPrice().equals(getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(/*task, */name, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                //.append(" Task: " + getTask().getName())
                .append(" Price: " + getPrice());
        return builder.toString();
    }
}
