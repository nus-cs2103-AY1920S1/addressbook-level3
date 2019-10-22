package seedu.address.model.itineraryitem.accommodation;

import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.ItineraryItem;
import seedu.address.model.tag.Tag;

/**
 * Represents an Accommodation in the trip planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Accommodation extends ItineraryItem {

    /**
     * Every field must be present and not null.
     */
    public Accommodation(Name name, Address address, Contact contact, Set<Tag> tags) {
        super(name, address, contact, tags);
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
}
