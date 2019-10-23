package seedu.address.model.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.person.Person;
import seedu.address.model.util.Date;

/**
 * API of Transaction.
 */
public abstract class Transaction implements UndoableAction {

    protected Amount amount;
    protected Date date;
    protected Person peopleInvolved;

    // Data fields
    private final Set<Category> categories = new HashSet<>();

    public Transaction(Amount amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Amount amount, Date date, Set<Category> categories) {
        this(amount, date);
        this.categories.addAll(categories);
    }

    public Transaction(Amount amount, Date date, Person personInvolved) {
        this(amount, date);
        this.peopleInvolved = personInvolved;
    }

    public Transaction(Amount amount, Date date, Set<Category> categories, Person personInvolved) {
        this(amount, date);
        this.categories.addAll(categories);
        this.peopleInvolved = personInvolved;
    }


    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTransaction(BankAccountOperation otherTransaction) {
        if (otherTransaction == this) {
            return true;
        }

        return otherTransaction != null
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getDate().equals(getDate());
    }

    @Override
    public abstract boolean equals(Object obj);

}
