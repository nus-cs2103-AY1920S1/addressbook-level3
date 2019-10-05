package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;

/**
 * Represents an expense by a person in an activity.
 * Guarantees: details are present and not null, field values are validated, immutable
 * except only isDeleted is mutable.
 */
public class Expense {
    private final double amount;
    private final Person person;
    private boolean isDeleted;

    public Expense(Person person, double amount) {
        requireAllNonNull(person);
        this.person = person;
        this.amount = amount;
        this.isDeleted = false;
    }

    public double getAmount() {
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
}
