package seedu.address.model.transaction;

import java.util.Date;

/**
 * SplitTransaction consists of Amount amount, Date date, int numOfSplits,
 * Amount splitAmount
 */
public class SplitTransaction extends Transaction{

    public int numOfSplits;
    private Amount splitAmount;

    public SplitTransaction(Amount amount, Date date, int numOfSplits) {
        super(amount, date);
        this.numOfSplits = numOfSplits;
        this.splitAmount = amount.divideAmount(numOfSplits);
    }

    /**
     * SplitTransaction does not change the overall balance of User.
     * @param balance Amount of balance prior to execution of SplitCommand
     * @return balance same Amount of balance as prior to execution
     */
    @Override
    public Amount handleBalance(Amount balance){
        return balance;
    }
}
