package seedu.moolah.logic.commands.statistics;

import java.util.Optional;

import seedu.moolah.model.expense.Timestamp;

/**
 * Stores the details of StatsTrendCommand. Each non-empty field value will
 * lead to a different interaction to construct the final interval of interest for the statistics
 */
public class StatsTrendDescriptor {

    private Timestamp startDate;
    private Timestamp endDate;
    private boolean mode;

    public StatsTrendDescriptor() {};

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



