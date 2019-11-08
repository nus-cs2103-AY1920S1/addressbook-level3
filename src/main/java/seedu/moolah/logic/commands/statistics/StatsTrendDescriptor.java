package seedu.moolah.logic.commands.statistics;

import java.util.Optional;

import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Timestamp;


public class StatsTrendDescriptor {
    //a class with no direct constructor but only setters
    //the default value it gets empty from is the other expense it is copying
    //it has access to a model object that is pulled from the index and then in the #execute can fetch alrdy

    private Timestamp startDate;
    private Timestamp endDate;
    private boolean mode;

    public StatsTrendDescriptor() {};

    //public StatsTrendDescriptor(St) Dont have a checking class
//    /**
//     * Returns true if at least one field is edited.
//     */
//    public boolean isAnyFieldEdited() {
//        return CollectionUtil.isAnyNonNull(description, price, category, timestamp);
//    }

    public StatsTrendDescriptor(Budget primaryBudget) {

    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public Optional<Timestamp> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    public Optional<Timestamp> getEndDate() {
        return Optional.ofNullable(endDate);
    }

    public boolean getMode() {
        return mode;
    }




    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatsTrendDescriptor)) {
            return false;
        }

        // state check
        StatsTrendDescriptor typeCasted = (StatsTrendDescriptor) other;

        return getEndDate().equals(typeCasted.getEndDate())
                && getStartDate().equals(typeCasted.getStartDate())
                && getMode() == typeCasted.getMode();
    }

}



