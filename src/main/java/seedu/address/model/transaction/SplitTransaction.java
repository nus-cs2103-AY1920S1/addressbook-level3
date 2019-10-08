package seedu.address.model.transaction;

import java.util.Date;

public class SplitTransaction extends Transaction{

    public int numOfSplits;

    /*
     *
     * @param numOfSplits will define the number of ways the same amount is going to be divided into
     */
    public SplitTransaction(Amount amount, Date date, int numOfSplits) {
        super(amount, date);
        this.numOfSplits = numOfSplits;
    }

    @Override
    public Amount handleBalance(Amount balance){
        Amount myNewBalance = balance.divideAmount(numOfSplits);
        return myNewBalance;
    }
}
