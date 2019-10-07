package seedu.address.model.transaction;

import java.util.Date;

public class InTransaction extends Transaction {
    InTransaction(int amount, Date date) {
        super(amount, date);
    }
    @Override
    public int handleBalance(int balance) {
        return balance + super.amount;
    }
}
