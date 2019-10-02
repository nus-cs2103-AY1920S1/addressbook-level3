package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Item in Elisa.
 * Guarantees: ItemDescription is present and not null. 
 * At least one of the following three fields (Task, Event, Reminder) is present and not null.
 */
public class Item {

    // Identity fields
    private final Task task;
    private final Event event;
    private final Reminder reminder;
    
    // Data fields
    private final ItemDescription itemDescription;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    private Item(Task task, Event event, Reminder reminder,
                 ItemDescription itemDescription, Set<Tag> tags) {
        requireAllNonNull(itemDescription, tags);
        this.task = task;
        this.event = event;
        this.reminder = reminder;
        
        this.itemDescription = itemDescription;
        this.tags.addAll(tags);
    }

    public boolean hasTask() { return optTask.isPresent(); }
    
    public boolean hasEvent() { return optEvent.isPresent(); }
    
    public boolean hasReminder() { return optReminder.isPresent(); }
    
    public Optional<Task> getTask() {
        if(this.task == null) {
            return Optional.empty()
        } else {
            return this.task;
        }
    }
    
    public Optional<Event> getEvent() {
        if(this.event == null) {
            return Optional.empty()
        } else {
            return this.event;
        }
    }
    
    public Optional<Reminder> getReminder() {
        if(this.reminder == null) {
            return Optional.empty()
        } else {
            return this.reminder;
        }
    }
    
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    /**
     * Change ItemDescription
     */
    public boolean setItemDescription(ItemDescription) {
        Item
    }

    /**
     * 
     */
    
    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean hasSameTask(Item otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
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

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getTask().equals(getTask())
                && otherItem.getReminder().equals(getReminder())
                && otherItem.getEvent().equals(getEvent())
                && otherItem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

public class ItemBuilder {

    // Identity fields
    private final Task task;
    private final Event event;
    private final Reminder reminder;

    // Data fields
    private final ItemDescriptor itemDescriptor;
    private final Set<Tag> tags = new HashSet<>();
    
    public ItemBuilder() {}
    
    public ItemBuilder hasTask(Task task) {
        requireNonNull(task);
        this.task = task;
    }
    
    public ItemBuilder hasEvent(Event event) {
        this.event = event;
    }
    
    public ItemBuilder hasReminder(Reminder reminder) {
        this.reminder = reminder;
    }
    
    public ItemBuilder hasDescriptor(String descriptor) {
        this.itemDescriptor = descriptor;
    }
    
    public ItemBuilder hasTags(Set<Tag> tags) {
        this.tags = tags;
    }
    
    public Item build() {
        Item newItem = new Item();
    }
    
}