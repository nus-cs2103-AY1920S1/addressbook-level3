package seedu.address.model.transaction;

import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SplitTransaction consists of Amount amount, Date date, List<Amount> splitAmount, and peopleInvolved
 */
public class SplitTransaction extends Transaction {

    private final List<Amount> splitAmounts;
    private final UniquePersonList peopleInvolved;

    public SplitTransaction(Amount amount, Date date, List<Integer> shares, UniquePersonList people) {
        super(amount, date);
        this.peopleInvolved = people;
        int denominator = shares.stream().mapToInt(i -> i).sum();
        splitAmounts = shares.stream()
                .map(share -> new Amount((double) share / denominator))
                .collect(Collectors.toList());
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
                    && peopleInvolved.equals(splitObj.peopleInvolved)
                    && splitAmounts == splitObj.splitAmounts;
        } else {
            return false;
        }
    }
}
