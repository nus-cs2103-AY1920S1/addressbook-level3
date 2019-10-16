package seedu.address.model.person;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages ExpenseTrackerList. Will be instantiated inside Object Manager and Address Book.
 */
public class ExpenseTrackerManager {
    private FilteredList<ExpenseTracker> trackerList;



    public ExpenseTrackerManager(ObservableList<ExpenseTracker> trackerList) {
        this.trackerList = new FilteredList<>(trackerList);
    }


    public ObservableList<ExpenseTracker> getList() {
        return trackerList;
    }

    /**
     * Iterates through list of trackers, and see which tracker(s) are keeping track of this event.
     * @param filteredExpenses
     */
    public void track(FilteredList<Expense> filteredExpenses) {
        for (ExpenseTracker tracker : trackerList) {
            long newAmt = 0;
            filteredExpenses.setPredicate(tracker.getPredicate());
            for (Expense expense : filteredExpenses) {
                newAmt += expense.getAmount().value;
            }
            tracker.setAmount(newAmt);
        }
    }
}
