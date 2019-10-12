package seedu.address.model.person;

/**
 * Represents a Doctor in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {

    /**
     * Every field must be present and not null.
     */
    public Doctor(Type type, Nric nric, Name name, Phone phone) {
        super(type, nric, name, phone);
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

        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return otherDoctor.getNric().equals(getNric())
                && otherDoctor.getName().equals(getName())
                && otherDoctor.getPhone().equals(getPhone())
                && otherDoctor.getType().equals(getType());
    }

    // Below methods intentionally created so developers will not forget to
    // edit these two methods if Doctor class changes.
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
