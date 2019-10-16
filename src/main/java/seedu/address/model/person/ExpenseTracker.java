package seedu.address.model.person;


/**
 * Expense Tracker used by expense reminder to trigger reminder.
 */
public class ExpenseTracker {
    private long currAmount;
    private ExpenseContainsTagPredicate predicate;

    public ExpenseTracker(ExpenseContainsTagPredicate predicate) {
        this.predicate = predicate;
        currAmount = 0;
    }

    public long getAmount() {
        return currAmount;
    }

    public void setAmount(long newAmt) {
        currAmount = newAmt;
    }

    public ExpenseContainsTagPredicate getPredicate() {
        return predicate;
    }

    /**
     * Used to compare if one tracker does the same thing as another.
     * @param otherTracker tracker to compare with
     * @return boolean value.
     */
    public boolean isSameEntry(ExpenseTracker otherTracker) {
        if (otherTracker == this) {
            return true;
        }

        return otherTracker != null
                && otherTracker.getPredicate().equals(getPredicate());
    }
}
