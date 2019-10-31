package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.ReminderTime;
import seedu.address.model.task.TaskTime;

/**
 * Jackson-friendly version of {@link TaskTime}.
 */
public class JsonAdaptedReminderTime implements Comparable<JsonAdaptedReminderTime> {
    private final String fullTime;

    /**
     * Constructs a {@code JsonAdaptedReminderTime} with the given {@code fullTime}.
     */
    @JsonCreator
    public JsonAdaptedReminderTime(String fullTime) {
        this.fullTime = fullTime;
    }

    /**
     * Converts a given {@code ReminderTime} into this class for Jackson use.
     */
    public JsonAdaptedReminderTime(ReminderTime source) {
        fullTime = source.fullTime;
    }

    @JsonValue
    public String getFullTime() {
        return fullTime;
    }

    /**
     * Converts this Jackson-friendly adapted reminder time object into the model's {@code ReminderTime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminderTime.
     */
    public ReminderTime toModelType() throws IllegalValueException {
        if (!ReminderTime.isValidReminderTime(fullTime)) {
            throw new IllegalValueException(ReminderTime.MESSAGE_CONSTRAINTS);
        }
        return new ReminderTime(fullTime);
    }

    @Override
    public int compareTo(JsonAdaptedReminderTime o) {
        try {
            ReminderTime thisReminderTime = this.toModelType();
            ReminderTime otherReminderTime = o.toModelType();
            return thisReminderTime.compareTo(otherReminderTime);
        } catch (IllegalValueException e) {
            return 0;
        }
    }
}
