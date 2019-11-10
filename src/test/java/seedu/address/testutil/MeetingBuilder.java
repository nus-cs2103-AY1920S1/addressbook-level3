package seedu.address.testutil;

import seedu.address.model.project.Meeting;
import seedu.address.model.project.Description;
import seedu.address.model.project.Time;

import java.text.ParseException;

public class MeetingBuilder {

    public static final String DEFAULT_TIME = "19/09/2019 1900";
    public static final String DEFAULT_DESCRIPTION = "Milestones discussions";

    private Time time;
    private Description description;

    public MeetingBuilder() throws ParseException {
        time = new Time(DEFAULT_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    public MeetingBuilder(Meeting meetingToCopy) {
        time = meetingToCopy.getTime();
        description = meetingToCopy.getDescription();
    }

    /**
     * Sets the {@code Time} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTime(String time) throws ParseException {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Meeting build() {
        return new Meeting(time, description);
    }
}
