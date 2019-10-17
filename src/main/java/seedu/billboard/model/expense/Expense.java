package seedu.billboard.model.expense;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.billboard.model.tag.Tag;

/**
 * Represents a Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {

    // Identity fields
    private Name name;
    private Description description;
    private Amount amount;
    private CreatedDateTime created;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private String archiveName;

    /**
     * Every field must be present and not null.
     */
    public Expense(Name name, Description description,
                   Amount amount, CreatedDateTime created,
                   Set<Tag> tags) {

        requireAllNonNull(name, description, created, amount, tags);
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.created = created;
        this.tags.addAll(tags);
        this.archiveName = "";
    }

    public Expense(Name name, Description description,
                   Amount amount, CreatedDateTime created,
                   Set<Tag> tags, String archiveName) {

        requireAllNonNull(name, description, amount);
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.created = created;
        this.tags.addAll(tags);
        this.archiveName = archiveName;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public CreatedDateTime getCreated() {
        return created;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public CreatedDateTime getCreatedDateTime() {
        return created;
    }

    public boolean isArchived() {
        return !archiveName.equals("");
    }

    public void archiveTo(String archiveName) {
        this.archiveName = archiveName;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getName().equals(getName())
                && otherExpense.getDescription().equals(getDescription())
                && otherExpense.getAmount().equals(getAmount())
                && otherExpense.getCreated().equals(getCreated())
                && otherExpense.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, amount, created, tags);
    }

    @Override
    public String toString() {
        return "Name: "
                + getName()
                + " Description: "
                + getDescription()
                + " Amount: "
                + getAmount()
                + " Created: "
                + getCreated()
                + " Tags: "
                + getTags();
    }
}
