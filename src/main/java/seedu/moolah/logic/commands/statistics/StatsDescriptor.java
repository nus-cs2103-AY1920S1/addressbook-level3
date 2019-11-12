package seedu.moolah.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.moolah.model.general.Timestamp;

/**
 * Stores the details of StatsCommand. Each non-empty field value will
 * lead to a different interaction to construct the final interval of interest for the statistics
 */
public class StatsDescriptor {

    private Timestamp startDate;
    private Timestamp endDate;

    public StatsDescriptor() {};

    /**
     * Copy constructor.
     */
    public StatsDescriptor(StatsDescriptor toCopy) {
        setStartDate(toCopy.startDate);
        setEndDate(toCopy.endDate);
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



    /**
     * Returns true if end date is before start date.
     */
    public boolean isEndBeforeStart() {
        requireNonNull(startDate);
        requireNonNull(endDate);
        return endDate.isBefore(startDate);
    }

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






