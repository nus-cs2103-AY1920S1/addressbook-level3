package seedu.address.model.transaction;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * SplitTransaction consists of Amount amount, Date date, List<Amount> splitAmount, and peopleInvolved
 */
public class SplitTransaction extends Transaction {

    private final List<Amount> splitAmounts;
    private final UniquePersonList peopleInvolved;

    public SplitTransaction(Amount amount, Date date, List<Integer> shares, UniquePersonList people) {
        super(amount, date);
        requireAllNonNull(shares, people);
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
