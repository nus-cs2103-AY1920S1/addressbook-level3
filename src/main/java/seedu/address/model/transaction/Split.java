package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

/**
 * Split consists of Amount amount, Date date, {@code List<Amount> splitAmount}, and peopleInvolved
 */
public class Split extends Transaction implements UndoableAction, LedgerOperation {

    private final List<Amount> splitAmounts;
    private final UniquePersonList peopleInvolved;

    public Split(Amount amount, Date date, Description description, List<Integer> shares, UniquePersonList people) {
        super(amount, date, description);
        requireAllNonNull(amount, date, shares, people);
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
     * Modifies balance of each Person involved in Split. Person is added
     * into Ledger's personList if not already inside.
     * @param balance initial balance in the Ledger
     * @param peopleInLedger UniqueList of people involved in the Split
     * @return updated balance after splitting
     *
     */
    @Override
    public Amount handleBalance(Amount balance, UniquePersonList peopleInLedger) {
        Iterator<Person> personInvolvedIterator = peopleInvolved.iterator();
        Iterator<Amount> amountIterator = splitAmounts.iterator();
        Amount newBalance = balance;
        while (personInvolvedIterator.hasNext()) {
            Amount expenditure = amountIterator.next();
            Person person = personInvolvedIterator.next();
            LendMoney transaction = new LendMoney(person, expenditure, this.description);
            newBalance = transaction.handleBalance(newBalance, peopleInLedger);
        }
        return newBalance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Split) {
            Split splitObj = (Split) obj;
            return this.amount.equals(splitObj.amount)
                    && this.date.equals(splitObj.date)
                    && peopleInvolved.equals(splitObj.peopleInvolved)
                    && splitAmounts == splitObj.splitAmounts;
        } else {
            return false;
        }
    }

    @Override
    public Amount getAmount() {
        return amount;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public UniquePersonList getPeopleInvolved() {
        return peopleInvolved;
    }
}
