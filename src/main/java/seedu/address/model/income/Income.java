package seedu.address.model.income;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.tag.Tag;



import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Income in the Financial Planner.
 */
public class Income {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Description description;

    //Data fields
    private final Amount amount;
    private final Set<Tag> tags = new HashSet<>();
    private final Date dateAdded;

    /**
     * Every field must be present and not null.
     */
    public Income(Name name, Phone phone, Description description, Amount amount, Set<Tag> tags) {
        requireAllNonNull(name, phone, description, amount, tags);
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.amount = amount;
        this.tags.addAll(tags);
        this.dateAdded = new Date();
    }

    public Description getDescription() {
        return description;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDateAdded() { return dateAdded; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * This defines a weaker notion of equality between two claims.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both incomes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two incomes.
     */
    public boolean isSameIncome(Income otherIncome) {
        if (otherIncome == this) {
            return true;
        }

        return otherIncome != null
                && otherIncome.getName().equals(getName())
                && (otherIncome.getDescription().equals(getDescription()) || otherIncome.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both incomes have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return otherIncome.getName().equals(getName())
                && otherIncome.getPhone().equals(getPhone())
                && otherIncome.getDescription().equals(getDescription())
                && otherIncome.getAmount().equals(getAmount())
                && otherIncome.getTags().equals(getTags())
                && otherIncome.getDateAdded().equals(getDateAdded());
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Dated Added: ")
                .append(getDateAdded())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
