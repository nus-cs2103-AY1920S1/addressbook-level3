package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

/**
 * Abstracted class to encourage code reuse in concrete classes
 * {@see LendMoney}
 * {@see ReceiveMoney}
 */
public abstract class Payment extends Transaction implements LedgerOperation {
    protected final Person person;

    public Payment(Person person, Amount amount, Date date) {
        super(amount, date);
        requireNonNull(person);
        this.person = person;

    }

    public Payment(Person person, Amount amount) {
        this(person, amount, Date.now());
    }


    /**
     * Adds target into UniquePersonList if not already there
     * @param peopleInLedger list of People in the Ledger
     * @return reference to said Person
     */
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
