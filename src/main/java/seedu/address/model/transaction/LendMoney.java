package seedu.address.model.transaction;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

public class LendMoney extends Payment {
    public LendMoney(Person person, Amount amount, Date date) {
        super(person, amount, date);
    }

    @Override
    public Amount handleBalance(Amount balance, UniquePersonList peopleInLedger) {
        Person target = super.handleTarget(peopleInLedger);
        target.receive(amount);
        return balance.subtractAmount(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof LendMoney) {
            LendMoney payment = (LendMoney) obj;
            return this.amount.equals(payment.amount)
                    && this.date.equals(payment.date)
                    && this.person.equals(payment.person);
        } else {
            return false;
        }
    }
}
