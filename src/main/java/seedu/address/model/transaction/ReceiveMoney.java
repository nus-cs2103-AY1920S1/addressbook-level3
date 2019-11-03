package seedu.address.model.transaction;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

import java.util.List;
import java.util.Optional;

/**
 * Concrete class to encapsulate payments from other People to self
 */
public class ReceiveMoney extends Payment {
    public ReceiveMoney(Person person, Amount amount, Date date, Description description) {
        super(person, amount, date, description);
    }

    public ReceiveMoney(Person person, Amount amount, Description description) {
        super(person, amount, description);
    }

    @Override
    public Amount handleBalance(Amount balance, UniquePersonList peopleInLedger) {
        Person target = super.handleTarget(peopleInLedger);
        target.spend(amount);
        return balance.addAmount(amount);
    }

    @Override
    public boolean isSameLedgerOperation(LedgerOperation ledgerOperation) {
        return this.equals(ledgerOperation);
    }

    @Override
    public Optional<List<Integer>> getShares() {
        return Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ReceiveMoney) {
            ReceiveMoney payment = (ReceiveMoney) obj;
            return this.amount.equals(payment.amount)
                && this.date.equals(payment.date)
                && this.person.equals(payment.person);
        } else {
            return false;
        }
    }
}
