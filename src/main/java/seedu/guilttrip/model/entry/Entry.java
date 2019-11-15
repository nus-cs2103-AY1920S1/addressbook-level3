package seedu.guilttrip.model.entry;

import static seedu.guilttrip.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.guilttrip.model.reminders.conditions.EntrySpecificCondition;
import seedu.guilttrip.model.tag.Tag;

/**
 * Represents an Entry in GuiltTrip.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {
    private boolean hasReminder;
    private String uniqueId;
    // Identity fields
    private final Category category;
    private final Description desc;
    private final Amount amt;
    private final Date date;
    private EntrySpecificCondition entryTracker;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */

    public Entry(Category category, Description desc, Date date, Amount amount, Set<Tag> tags) {
        requireAllNonNull(desc, date, amount, tags);
        this.category = category;
        this.desc = desc;
        this.amt = amount;
        this.date = date;
        this.tags.addAll(tags);
    }
    // For reminders to recognise the entry they are keeping track of.

    public boolean hasReminder() {
        return this.hasReminder;
    }

    public void setHasReminder(final boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public Category getCategory() {
        return category;
    }

    public Description getDesc() {
        return desc;
    }

    public Amount getAmount() {
        return amt;
    }

    public String getType() {
        return "entry";
    }

    public Date getDate() {
        return this.date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setTracker(EntrySpecificCondition tracker) {
        this.entryTracker = tracker;
        tracker.setEntry(this);
    }
    public Optional<EntrySpecificCondition> getTracker() {
        if (entryTracker == null) {
            return Optional.empty();
        } else {
            return Optional.of(entryTracker);
        }
    }

    /**
     * Returns a new Entry if and only if it's category is edited.
     */
    public Entry modifiedCategory(String newName) {
        Category newCategory = new Category(newName, category.getCategoryType());
        return new Entry(newCategory, this.desc, this.date, this.amt, this.tags);
    }

    /**
     * Returns true if both entries of the same name have at least one other identity field that is the same.
     */
    public boolean isSameEntry(Entry otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return otherEntry != null
                && otherEntry.getType().equals(getType())
                && otherEntry.getCategory().equals(getCategory())
                && otherEntry.getDesc().equals(getDesc())
                && otherEntry.getAmount().equals(getAmount())
                && otherEntry.getTags().equals(getTags())
                && otherEntry.getDate().equals(getDate());
    }

    /**
     * Returns true if both entries have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(equalClass(other))) {
            return false;
        } else {
            if (this instanceof Expense) {
                return this.equals(other);
            } else if (this instanceof Income) {
                return this.equals(other);
            } else if (this instanceof Wish) {
                return this.equals(other);
            } else if (this instanceof Budget) {
                return this.equals(other);
            } else {
                return false;
            }
        }
    }

    /**
     * Checks if children class of this instance is same as that of other.
     * @param other object to compare to.
     * @return boolean.
     */
    protected boolean equalClass(Object other) {
        if (this instanceof Expense && !(other instanceof Expense)) {
            return false;
        } else if (this instanceof Income && !(other instanceof Income)) {
            return false;
        } else if (this instanceof Wish && !(other instanceof Wish)) {
            return false;
        } else {
            return !(this instanceof Budget) || other instanceof Budget;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(desc, date, amt, category, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc())
                .append(" Amount: ")
                .append(amt)
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
