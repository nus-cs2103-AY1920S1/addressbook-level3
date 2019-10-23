package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Date;

/**
 * Handles out transactions.
 */
public class OutTransaction extends Transaction implements BankAccountOperation {
    public OutTransaction(Amount amount, Date date) {
        super(amount.makeNegative(), date);
    }

    public OutTransaction(Amount amount, Date date, Set<Tag> tags) {
        super(amount.makeNegative(), date, tags);
    }

    public OutTransaction(Amount amount, Date date, Set<Tag> tags, Person personInvolved) {
        super(amount.makeNegative(), date, tags, personInvolved);
    }

    @Override
    public Amount handleBalance(Amount balance) {
        Amount newBalance = balance.addAmount(super.amount);
        return newBalance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof OutTransaction) {
            OutTransaction inObj = (OutTransaction) obj;
            return super.amount.equals(inObj.amount)
                    && super.date.equals(inObj.date)
                    && super.peopleInvolved.equals(inObj.peopleInvolved);
        } else {
            return false;
        }
    }
}
