package seedu.address.model.accommodation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents an Accommodation in the trip planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Accommodation {

    //Identity fields
    private final Name name;

    //Data fields
    private final Address address;
    private final Contact contact;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Accommodation(Name name, Address address, Contact contact, Set<Tag> tags) {

        requireAllNonNull(name, address, tags);
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Optional<Contact> getContact() {
        return Optional.ofNullable(contact); }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAccommodation(Accommodation otherAccommodation) {
        if (otherAccommodation == this) {
            return true;
        }

        return otherAccommodation != null
                && otherAccommodation.getName().equals(getName())
                && (otherAccommodation.getAddress().equals(getAddress()));
    }

    /**
     * Returns true if both activities have the same identity and data fields.
     * This defines a stronger notion of equality between two activities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Accommodation)) {
            return false;
        }

        Accommodation otherAccommodation = (Accommodation) other;
        return otherAccommodation.getName().equals(getName())
                && otherAccommodation.getAddress().equals(getAddress())
                && otherAccommodation.getTags().equals(getTags())
                && otherAccommodation.getContact().equals(getContact());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
