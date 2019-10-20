package seedu.moneygowhere.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.reminder.ReminderMessage;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.commons.exceptions.IllegalValueException;

public class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String deadline;
    private final String reminderMessage;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given Reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("deadline") String deadline,
                               @JsonProperty("reminderMessage") String reminderMessage) {
        this.deadline = deadline;
        this.reminderMessage = reminderMessage;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        deadline = source.getDeadline().value;
        reminderMessage = source.getReminderMessage().value;
    }

    /**
     * Converts this Jackson-friendly adapted Reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Reminder.
     */
    public Reminder toModelType() throws IllegalValueException {

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(deadline)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDeadline = new Date(deadline);

        if (reminderMessage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }

        final ReminderMessage modelReminderMessage = new ReminderMessage(reminderMessage);

        return new Reminder(modelDeadline, modelReminderMessage);
    }
}
