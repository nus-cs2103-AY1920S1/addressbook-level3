package seedu.address.model.itineraryitem.activity;

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
public class Activity extends ItineraryItem {

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Address address, Contact contact, Set<Tag> tags) {
        super(name, address, contact, tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameActivity(Activity otherActivity) {
        if (otherActivity == this) {
            return true;
        }

        return otherActivity != null
                && otherActivity.getName().equals(getName())
                && (otherActivity.getAddress().equals(getAddress()));
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

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.getName().equals(getName())
                && otherActivity.getAddress().equals(getAddress())
                && otherActivity.getTags().equals(getTags())
                && otherActivity.getContact().equals(getContact());
    }
}
