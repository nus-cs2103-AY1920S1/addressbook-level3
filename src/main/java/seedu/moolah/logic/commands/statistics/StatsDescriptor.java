package seedu.moolah.logic.commands.statistics;

import java.util.Optional;

import org.antlr.runtime.misc.Stats;

import seedu.moolah.commons.util.CollectionUtil;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

public class StatsDescriptor {
    //a class with no direct constructor but only setters
    //the default value it gets empty from is the other expense it is copying
    //it has access to a model object that is pulled from the index and then in the #execute can fetch alrdy

    private Timestamp startDate;
    private Timestamp endDate;

    public StatsDescriptor() {};

    //public StatsDescriptor(St) Dont have a checking class
//    /**
//     * Returns true if at least one field is edited.
//     */
//    public boolean isAnyFieldEdited() {
//        return CollectionUtil.isAnyNonNull(description, price, category, timestamp);
//    }

    public StatsDescriptor(Budget primaryBudget) {

    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Optional<Timestamp> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    public Optional<Timestamp> getEndDate() {
        return Optional.ofNullable(endDate);
    }

//    public boolean isAnyFieldEdited() {
//        return CollectionUtil.isAnyNonNull(startDate, endDate);
//    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatsDescriptor)) {
            return false;
        }

        // state check
        StatsDescriptor typeCasted = (StatsDescriptor) other;

        return getEndDate().equals(typeCasted.getEndDate())
                && getStartDate().equals(typeCasted.getStartDate());
    }

}



