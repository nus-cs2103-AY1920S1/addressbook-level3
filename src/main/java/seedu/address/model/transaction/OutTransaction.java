package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.person.Person;
import seedu.address.model.util.Date;

/**
 * Handles out transactions.
 */
public class OutTransaction extends Transaction implements BankAccountOperation {
    public OutTransaction(Amount amount, Date date, Description description) {
        super(amount.makeNegative(), date, description);
    }

    public OutTransaction(Amount amount, Date date, Description description, Set<Category> categories) {
        super(amount.makeNegative(), date, description, categories);
    }

    public OutTransaction(Amount amount, Date date, Description description,
                          Set<Category> categories, Person personInvolved) {
        super(amount.makeNegative(), date, description, categories, personInvolved);

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
                // && super.peopleInvolved.equals(inObj.peopleInvolved) // TODO
                && super.description.equals(inObj.description)
                && super.categories.equals(inObj.categories);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("Out transaction of %s", super.toString());
    }
}
