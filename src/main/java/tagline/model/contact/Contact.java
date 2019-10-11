package tagline.model.contact;

import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Contact(Name name, Phone phone, Email email, Address address, Description description) {
        requireAllNonNull(name, phone, email, address, description);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both contacts of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getName().equals(getName())
                && (otherContact.getPhone().equals(getPhone()) || otherContact.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return otherContact.getName().equals(getName())
                && otherContact.getPhone().equals(getPhone())
                && otherContact.getEmail().equals(getEmail())
                && otherContact.getAddress().equals(getAddress())
                && otherContact.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
