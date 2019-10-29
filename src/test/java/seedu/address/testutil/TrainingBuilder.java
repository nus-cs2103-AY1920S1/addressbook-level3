package seedu.address.testutil;

import java.util.HashMap;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

public class TrainingBuilder {
    private AthletickDate date;
    private HashMap<Person, Boolean> attendance;

    public TrainingBuilder() throws ParseException {
        this.date = new AthletickDate("00000000");
        this.attendance = new HashMap<>();
    }

    public TrainingBuilder(Training training) {
        this.date = training.getDate();
        this.attendance = training.getTrainingAttendance();
    }

    public TrainingBuilder withDate(AthletickDate date) {
        this.date = date;
        return this;
    }

    public TrainingBuilder withAttendance(HashMap<Person, Boolean> attendance) {
        this.attendance = attendance;
        return this;
    }

    public Training build() {
        return new Training(this.date, this.attendance);
    }
}
