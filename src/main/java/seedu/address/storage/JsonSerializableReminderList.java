package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.calendar.Reminder;

/**
 * A list of reminders that is serializable to JSON format.
 */
@JsonRootName(value = "reminderlist")
public class JsonSerializableReminderList {

    public static final String LIST_CONTAINS_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminderList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReminderList} with the given reminders.
     */
    @JsonCreator
    public JsonSerializableReminderList(@JsonProperty("reminderList") List<JsonAdaptedReminder> reminders) {
        this.reminderList.addAll(reminders);
    }

    /**
     * Converts a given list of reminders into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created list of reminders.
     */
    public JsonSerializableReminderList(ReadOnlyCalendar source) {
        this.reminderList.addAll(source.getCalendarEntryList().stream()
            .map(calendarEntry -> (Reminder) calendarEntry)
            .map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this list of reminders into the sugarmummy.recmfood.model's {@code Reminder} objects.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReadOnlyCalendar toModelType() throws IllegalValueException {
        Calendar reminders = new Calendar();

        for (JsonAdaptedReminder jsonAdaptedReminder : reminderList) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (reminders.hasCalendarEntry(reminder)) {
                throw new IllegalValueException(LIST_CONTAINS_DUPLICATE_REMINDER);
            }
            reminders.addCalendarEntry(reminder);
        }
        return reminders;
    }
}
