package seedu.address.model.transaction;

import java.util.Date;

public class OutTransaction extends Transaction{
    public OutTransaction(Amount amount, Date date) {
        super(amount, date);
    }

    @Override
    public Amount handleBalance(Amount balance) {
        Amount newBalance = balance.subtractAmount(super.amount);
        return newBalance;
    }
}
