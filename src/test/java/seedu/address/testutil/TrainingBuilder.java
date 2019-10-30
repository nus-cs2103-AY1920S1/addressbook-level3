package seedu.address.testutil;

import java.util.HashMap;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * A utility class to help with building Training objects.
 */
public class TrainingBuilder {
    private AthletickDate date;
    private HashMap<Person, Boolean> attendance;

    public TrainingBuilder() {
    }

    /**
     * Initializes the TrainingBuilder with the data of {@code training}.
     */
    public TrainingBuilder(Training training) {
        this.date = training.getDate();
        this.attendance = training.getTrainingAttendance();
    }

    /**
     * Sets the {@code date} of the {@code Training} that we are building.
     */
    public TrainingBuilder withDate(AthletickDate date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code attendance} of the {@code Training} that we are building.
     */
    public TrainingBuilder withAttendance(HashMap<Person, Boolean> attendance) {
        this.attendance = attendance;
        return this;
    }

    public Training build() {
        return new Training(this.date, this.attendance);
    }
}
