package seedu.address.model.transaction;

import java.util.Date;

public class OutTransaction extends Transaction{
    public OutTransaction(int amount, Date date) {
        super(amount, date);
    }

    @Override
    public int handleBalance(int balance) {
        return balance - super.amount;
    }
}
