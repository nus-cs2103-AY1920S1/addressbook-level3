package seedu.address.model.transaction;

import java.util.Date;

public class InTransaction extends Transaction {
    InTransaction(Amount amount, Date date) {
        super(amount, date);
    }

    @Override
    public Amount handleBalance(Amount balance) {
        Amount newBalance = balance.addAmount(super.amount);
        return newBalance;
    }
}
