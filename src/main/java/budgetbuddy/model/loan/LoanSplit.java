package budgetbuddy.model.loan;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Represents money owed between two people when splitting a payment.
 */
public class LoanSplit {

    private final Person debtor;
    private final Person creditor;
    private final Amount amount;

    /**
     * Every field must be present and non-null.
     */
    public LoanSplit(Person debtor, Person creditor, Amount amount) {
        requireAllNonNull(debtor, creditor, amount);
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
    }

    public Person getDebtor() {
        return debtor;
    }

    public Person getCreditor() {
        return creditor;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanSplit)) {
            return false;
        }

        LoanSplit otherLoanSplit = (LoanSplit) other;
        return debtor.equals(otherLoanSplit.debtor)
                && creditor.equals(otherLoanSplit.creditor)
                && amount.equals(otherLoanSplit.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtor, creditor, amount);
    }

    @Override
    public String toString() {
        return debtor + " owes " + creditor + " " + amount;
    }
}
