package seedu.address.model.transaction;

import seedu.address.model.person.Person;

import java.util.Date;

public abstract class Transaction {
    public int amount;
    public Date date;
    Person peopleInvolved;

    public Transaction(int amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    public Transaction(int amount, Date date, Person personInvolved) {
        this(amount, date);
        this.peopleInvolved = personInvolved;
    }

    public abstract int handleBalance(int balance);
}
