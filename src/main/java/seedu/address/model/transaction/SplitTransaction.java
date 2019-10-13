package seedu.address.model.transaction;

import java.util.Date;

/**
 * SplitTransaction consists of Amount amount, Date date, int numOfSplits, Amount splitAmount
 */
public class SplitTransaction extends Transaction {

    private final int numOfSplits;
    private final Amount splitAmount;

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
    public Amount handleBalance(Amount balance) {
        return balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SplitTransaction) {
            SplitTransaction splitObj = (SplitTransaction) obj;
            return super.amount.equals(splitObj.amount)
                    && super.date.equals(splitObj.date)
                    && super.peopleInvolved.equals(splitObj.peopleInvolved)
                    && numOfSplits == splitObj.numOfSplits
                    && splitAmount.equals(splitObj.splitAmount);
        } else {
            return false;
        }
    }
}
