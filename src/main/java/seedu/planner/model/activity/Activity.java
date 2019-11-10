package seedu.planner.model.activity;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

//@@author oscarsu97

/**
 * Represents an Activity in the trip planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Activity implements Comparable<Activity> {

    private final Name name;
    private final Address address;
    private final Contact contact;
    private final Cost cost;
    private Set<Tag> tags;
    private final Duration duration;
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Address address, Contact contact, Cost cost, Set<Tag> tags, Duration duration,
                    Priority priority) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.cost = cost;
        this.tags = tags;
        this.duration = duration;
        this.priority = priority;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Optional<Contact> getContact() {
        return Optional.ofNullable(contact);
    }

    public Optional<Cost> getCost() {
        return Optional.ofNullable(cost);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Duration getDuration() {
        return duration;
    }

    public Priority getPriority() {
        return priority;
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

    @Override
    public int hashCode() {
        return Objects.hash(name, address, contact, tags, duration, priority, cost);
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

        if (other instanceof ActivityWithTime) {
            return this.equals(((ActivityWithTime) other).getActivity());
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;
        return otherActivity.getName().equals(getName())
                && otherActivity.getAddress().equals(getAddress())
                && otherActivity.getTags().equals(getTags())
                && otherActivity.getContact().equals(getContact())
                && otherActivity.getCost().equals(getCost())
                && otherActivity.getDuration().equals(getDuration())
                && otherActivity.getPriority().equals(getPriority());
    }

    @Override
    public int compareTo(Activity o) {
        int thisPriority = priority.priorityValue;
        int thatPriority = o.priority.priorityValue;
        if (thisPriority > 0) {
            if (thatPriority <= 0) {
                return -1;
            } else {
                if (thisPriority - thatPriority > 0) {
                    return 1;
                }
                if (thisPriority - thatPriority < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Location: ")
                .append(getAddress())
                .append(" Phone: ")
                .append(getContact().isPresent()
                        ? getContact().get().getPhone()
                        : "")
                .append(" Cost: $")
                .append(getCost().isPresent() ? getCost().get() : 0.00)
                .append(" Duration: ")
                .append(getDuration())
                .append(" Priority: ")
                .append(getPriority())
                //note that Contact.toString also has tags
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
