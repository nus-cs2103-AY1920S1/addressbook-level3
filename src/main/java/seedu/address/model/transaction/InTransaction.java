package seedu.address.model.transaction;

import java.util.Date;

/**
 * Handles in transactions.
 */
public class InTransaction extends Transaction {
    public InTransaction(Amount amount, Date date) {
        super(amount, date);
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
