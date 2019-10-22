package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

public abstract class Payment extends Transaction implements LedgerOperation{
    protected final Person person;

    public Payment(Person person, Amount amount, Date date) {
        super(amount, date);
        requireNonNull(person);
        this.person = person;

    }

    public Payment(Person person, Amount amount) {
        this(person, amount, Date.now());
    }



    protected Person handleTarget(UniquePersonList peopleInLedger) {
        Person personInvolved = person;
        if (peopleInLedger.contains(person)) {
            personInvolved = peopleInLedger.get(person).get();
        } else {
            peopleInLedger.add(person);
        }
        return personInvolved;
    }
}
