package seedu.exercise.testutil.builder;

import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.typicalutil.TypicalDates;
import seedu.exercise.testutil.typicalutil.TypicalRegime;

/**
 * Builder for {@code Schedule} objects.
 */
public class ScheduleBuilder {

    private Regime regime = TypicalRegime.VALID_REGIME_CARDIO;
    private Date date = TypicalDates.DATE_1;

    /**
     * Sets the regime of the schedule object to {@code regime}.
     */
    public ScheduleBuilder withRegime(Regime regime) {
        this.regime = regime;
        return this;
    }

    /**
     * Sets the date of the schedule object to {@code date}.
     */
    public ScheduleBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public Schedule build() {
        return new Schedule(regime, date);
    }
}
