package seedu.deliverymans.model.deliveryman;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;

/**
 * Represents a deliveryman
 * Allow deliveryman to update status? or admin is responsible for that when order is complete?
 */
public class Deliveryman {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final DeliveryHistory deliveryHistory;
    private final boolean isAvailable;

    /**
     * Every field must be present and not null.
     */
    public Deliveryman(Name name, Phone phone) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        deliveryHistory = null;
        isAvailable = false;
    }

    public void setStatusTo(String status) {} // this class should have all attributes as final?

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns true if both persons have the same identity. Data fields need not be same.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deliveryman)) {
            return false;
        }

        Deliveryman otherPerson = (Deliveryman) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone);
    }
}
