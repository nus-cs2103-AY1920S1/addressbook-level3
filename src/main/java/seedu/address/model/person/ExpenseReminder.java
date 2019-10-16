package seedu.address.model.person;

/**
 * Implement expense reminder
 */
public class ExpenseReminder extends Reminder {
    private long currSum;
    private long quota;
    private ExpenseTracker tracker;

    public ExpenseReminder(String message, long quota, ExpenseTracker tracker) {
        super(message);
        this.tracker = tracker;
        this.quota = quota;
        currSum = tracker.getAmount();
    }


    public long getSum() {
        return currSum;
    }

    public long getQuota() {
        return quota;
    }

    public ExpenseTracker getTracker() {
        return tracker;
    }

    /**
     *checks status of reminder. i.e. should reminder trigger.
     */
    public void updateStatus() {
        currSum = tracker.getAmount();
        super.setStatus(currSum >= quota);
    }

    @Override
    /**
     * Returns true if both ExpenseReminders have all identity fields that are the same.
     * @param otherExpenseReminder ExpenseReminder to compare to
     * @return boolean
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }
        if (!(otherReminder instanceof ExpenseReminder)) {
            return false;
        }
        ExpenseReminder otherExpenseReminder = (ExpenseReminder) otherReminder;
        return otherExpenseReminder != null
                && otherExpenseReminder.getMessage().equals(getMessage())
                && otherExpenseReminder.getTracker().equals(getTracker())
                && otherExpenseReminder.getQuota() == (getQuota());
    }
}
