package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Payment(Person person, Amount amount, Date date, Description description) {
        super(amount, date, description);
        requireNonNull(person);
        this.person = person;

    }

    public Payment(Person person, Amount amount, Description description) {
        this(person, amount, Date.now(), description);
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
            person.resetBalance();
            peopleInLedger.add(person);
        }
        return personInvolved;
    }


    @Override
    public UniquePersonList getPeopleInvolved() {
        UniquePersonList list = new UniquePersonList();
        list.add(person);
        return list;
    }

    @Override
    public List<Integer> getShares() {
        return IntStream.of(1).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Amount> getAmounts() {
        return List.of(amount);
    }
}
