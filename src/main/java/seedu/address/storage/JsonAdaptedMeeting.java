package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Description;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Time;

import java.text.ParseException;

class JsonAdaptedMeeting {

    private final String time;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("time") String time, @JsonProperty("description") String description) {
        this.time = time;
        this.description = description;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        time = source.getTime().time;
        description = source.getDescription().description;
    }

    //@JsonValue => is for the key : value pairs mapping, there can only be one in each json file.
    public String getTime() {
        return time;
    }

    //@JsonValue
    public String getDescription() {
        return description;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Meeting toModelType() throws IllegalValueException, ParseException {
        String t = time.trim().split(" ")[1];
        String d = time.trim().split(" ")[0];
        boolean checkLength = time.trim().split(" ").length < 2;
        if (checkLength || time == null || time.equals("")) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (!Time.isValidTimeAndDate(time.trim())) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (!Time.isValidDate(d)) {
            throw new IllegalValueException(Time.DATE_CONSTRAINTS);
        }
        if (!Time.isValidTime(t)) {
            throw new IllegalValueException(Time.TIME_CONSTRAINTS);
        }
        if ((!Description.isValidDescription(description))) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        Time time = new Time(this.time);
        Description description = new Description(this.description);
        return new Meeting(time, description);
    }
}
