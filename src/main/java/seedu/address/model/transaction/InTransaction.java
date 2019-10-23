package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.person.Person;
import seedu.address.model.util.Date;

/**
 * Handles in transactions.
 */
public class InTransaction extends Transaction implements BankAccountOperation {
    public InTransaction(Amount amount, Date date) {
        super(amount, date);
    }

    public InTransaction(Amount amount, Date date, Set<Category> categories) {
        super(amount, date, categories);
    }

    public InTransaction(Amount amount, Date date, Set<Category> categories, Person personInvolved) {
        super(amount, date, categories, personInvolved);
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
        } else if (obj instanceof InTransaction) {
            InTransaction inObj = (InTransaction) obj;
            return super.amount.equals(inObj.amount)
                    && super.date.equals(inObj.date)
                    && super.peopleInvolved.equals(inObj.peopleInvolved);
        } else {
            return false;
        }
    }
}
