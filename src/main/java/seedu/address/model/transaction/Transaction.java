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
    protected Description description;
    protected Person peopleInvolved;

    // Data fields
    protected final Set<Category> categories = new HashSet<>();

    public Transaction(Amount amount, Date date, Description description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Transaction(Amount amount, Date date, Description description, Set<Category> categories) {
        this(amount, date, description);
        this.categories.addAll(categories);
    }

    public Transaction(Amount amount, Date date, Description description, Person personInvolved) {
        this(amount, date, description);
        this.peopleInvolved = personInvolved;
    }

    public Transaction(Amount amount, Date date, Description description,
                       Set<Category> categories, Person personInvolved) {
        this(amount, date, description);
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

    public Description getDescription() {
        return description;
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
            && otherTransaction.getDate().equals(getDate())
            && otherTransaction.getDescription().equals(getDescription());
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public String toString() {
        return String.format("%s $%s on %s", this.description.toString(), this.amount.toString(), this.date.toString());
    }

}
