package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;

/**
 * Represents an expense by a person in an activity.
 * Guarantees: details are present and not null, field values are validated, immutable
 * except only isDeleted is mutable.
 */
public class Expense {
    private final Amount amount;
    private final Person person;
    private boolean isDeleted;

    public Expense(Person person, Amount amount) {
        requireAllNonNull(person, amount);
        this.person = person;
        this.amount = amount;
        this.isDeleted = false;
    }

    public Amount getAmount() {
        return amount;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Marks an expense as deleted for soft-deleting expenses.
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * Returns true if both expenses contain the same person and amount.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Expense) {
            Expense otherExpense = (Expense) other;
            return otherExpense.getPerson().equals(getPerson())
                    && otherExpense.getAmount().equals(getAmount());
        } else {
            return false;
        }
    }
}
