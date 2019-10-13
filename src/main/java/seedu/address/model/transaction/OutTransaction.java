package seedu.address.model.transaction;

import java.util.Date;

/**
 * Handles out transactions.
 */
public class OutTransaction extends Transaction {
    public OutTransaction(Amount amount, Date date) {
        super(amount, date);
    }

    @Override
    public Amount handleBalance(Amount balance) {
        Amount newBalance = balance.subtractAmount(super.amount);
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
