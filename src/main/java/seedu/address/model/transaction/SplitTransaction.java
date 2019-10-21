package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

/**
 * SplitTransaction consists of Amount amount, Date date, {@code List<Amount> splitAmount}, and peopleInvolved
 */
public class SplitTransaction extends Transaction {

    private final List<Amount> splitAmounts;
    private final UniquePersonList peopleInvolved;

    public SplitTransaction(Amount amount, Date date, List<Integer> shares, UniquePersonList people) {
        super(amount, date);
        requireAllNonNull(shares, people);
        this.peopleInvolved = people;
        int denominator = shares.stream().mapToInt(i -> i).sum();
        List<Amount> amounts = shares.stream()
                .map(share -> amount.byShare((double) share / denominator))
                .collect(Collectors.toList());
        splitAmounts = rebalanceAmounts(amount, amounts);
    }

    /**
     * Adds missing amount to the first Amount in List due to rounding errors in division
     * @param amount Amount that list to sum to
     * @param amounts List of Amounts
     * @return List of Amounts that sum to amount
     */
    private List<Amount> rebalanceAmounts(Amount amount, List<Amount> amounts) {
        Amount total = amounts.stream().reduce(new Amount(0), (amount1, amount2) -> amount1.addAmount(amount2));
        if (!total.equals(amount)) {
            Amount difference = amount.subtractAmount(total);
            amounts.set(0, amounts.get(0).addAmount(difference));
        }
        return amounts;
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

    /**
     * Modifies balance of each Person involved in SplitTransaction. Person is added
     * into Ledger's personList if not already inside.
     * @param balance Total spending in the SplitTransaction
     * @param peopleInLedger UniqueList of people involved in the SplitTransaction
     * @return updated balance after splitting
     *
     */
    public Amount handleBalance(Amount balance, UniquePersonList peopleInLedger) {
        Iterator<Person> personInvolvedIterator = peopleInvolved.iterator();
        Iterator<Amount> amountIterator = splitAmounts.iterator();
        while (personInvolvedIterator.hasNext()) {
            Amount expenditure = amountIterator.next();
            Person person = personInvolvedIterator.next();
            if (peopleInLedger.contains(person)) {
                person = peopleInLedger.get(person).get();
            } else {
                peopleInLedger.add(person);
            }
            balance = balance.subtractAmount(expenditure);
            person.handleExpense(expenditure);
        }
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
