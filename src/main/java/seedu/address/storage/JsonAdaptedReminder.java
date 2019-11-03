package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.reminder.ReminderTime;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String description;
    private final List<JsonAdaptedReminderTime> reminderTimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("description") String description,
                           @JsonProperty("reminderTimes") List<JsonAdaptedReminderTime> reminderTimes) {
        this.description = description;
        if (reminderTimes != null) {
            this.reminderTimes.addAll(reminderTimes);
        }
        Collections.sort(reminderTimes);
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        description = source.getDescription().fullReminderDescription;
        reminderTimes.addAll(source.getTime().stream().map(JsonAdaptedReminderTime::new).collect(Collectors.toList()));
    }
    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {
        final TreeSet<ReminderTime> times = new TreeSet<>(ReminderTime::compareTo);
        for (JsonAdaptedReminderTime time : reminderTimes) {
            times.add(time.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReminderDescription.class.getSimpleName()));
        }

        final ReminderDescription modelDescription = new ReminderDescription(description);

        final TreeSet<ReminderTime> modelTimes = new TreeSet<>(times);

        return new Reminder(modelDescription, modelTimes);
    }

}
