package seedu.address.model.transaction;

import java.util.Set;

import seedu.address.model.category.Category;
import seedu.address.model.person.Person;
import seedu.address.model.util.Date;

/**
 * Handles in transactions.
 */
public class InTransaction extends Transaction implements BankAccountOperation {
    public InTransaction(Amount amount, Date date, Description description) {
        super(amount, date, description);
    }

    public InTransaction(Amount amount, Date date, Description description, Set<Category> categories) {
        super(amount, date, description, categories);
    }

    public InTransaction(Amount amount, Date date, Description description,
                         Set<Category> categories, Person personInvolved) {
        super(amount, date, description, categories, personInvolved);
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
                // && super.peopleInvolved.equals(inObj.peopleInvolved) // TODO: CONFIRM?
                && super.description.equals(inObj.description)
                && super.categories.equals(inObj.categories);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("In transaction of %s", super.toString());
    }
}
