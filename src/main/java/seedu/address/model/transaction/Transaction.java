package seedu.address.model.transaction;

import java.util.Date;

import seedu.address.model.person.Person;

/**
 * API of Transaction.
 */
public abstract class Transaction {

    protected Amount amount;
    protected Date date;
    protected Person peopleInvolved;

    public Transaction(Amount amount, Date date) {
        this.amount = amount;
        this.date = date;
        this.peopleInvolved = null;
    }

    public Transaction(Amount amount, Date date, Person personInvolved) {
        this(amount, date);
        this.peopleInvolved = personInvolved;
    }

    public abstract Amount handleBalance(Amount balance);

    @Override
    public abstract boolean equals(Object obj);
}
