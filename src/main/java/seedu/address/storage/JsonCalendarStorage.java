package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Event;

/**
 * A class to access Calendar data stored as a json file on the hard disk.
 */
public class JsonCalendarStorage implements CalendarStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonCalendarStorage.class);
    private Path eventFilePath;
    private Path reminderFilePath;

    public JsonCalendarStorage(Path eventFilePath, Path reminderFilePath) {
        this.eventFilePath = eventFilePath;
        this.reminderFilePath = reminderFilePath;
    }

    public Path getEventFilePath() {
        return eventFilePath;
    }

    public Path getReminderFilePath() {
        return reminderFilePath;
    }


    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(eventFilePath, reminderFilePath);
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(Path eventFilePath, Path reminderFilePath)
        throws DataConversionException, IOException {
        requireAllNonNull(eventFilePath, reminderFilePath);

        Optional<JsonSerializableEventList> jsonEventList =
            JsonUtil.readJsonFile(eventFilePath, JsonSerializableEventList.class);

        Optional<JsonSerializableReminderList> jsonReminderList =
            JsonUtil.readJsonFile(reminderFilePath, JsonSerializableReminderList.class);

        if (jsonEventList.isEmpty() && jsonReminderList.isEmpty()) {
            return Optional.empty();
        }

        Calendar calendar = new Calendar();
        if (jsonEventList.isPresent()) {
            try {
                ReadOnlyCalendar events = jsonEventList.get().toModelType();
                for (CalendarEntry calendarEntry : events.getCalendarEntryList()) {
                    calendar.addCalendarEntry(calendarEntry);
                }
            } catch (IllegalValueException e) {
                logger.info("Illegal values found in " + eventFilePath + ": " + e.getMessage());
                throw new DataConversionException(e);
            }
        }

        if (jsonReminderList.isPresent()) {
            try {
                ReadOnlyCalendar reminders = jsonReminderList.get().toModelType();
                for (CalendarEntry calendarEntry : reminders.getCalendarEntryList()) {
                    calendar.addCalendarEntry(calendarEntry);
                }
            } catch (IllegalValueException e) {
                logger.info("Illegal values found in " + reminderFilePath + ": " + e.getMessage());
                throw new DataConversionException(e);
            }
        }
        return Optional.of(calendar);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        saveCalendar(calendar, eventFilePath, reminderFilePath);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar, Path eventFilePath, Path reminderFilePath) throws IOException {
        requireAllNonNull(calendar, eventFilePath, reminderFilePath);

        FileUtil.createIfMissing(eventFilePath);
        FileUtil.createIfMissing(reminderFilePath);

        Calendar events = new Calendar();
        Calendar reminders = new Calendar();

        for (CalendarEntry calendarEntry : calendar.getCalendarEntryList()) {
            if (calendarEntry instanceof Event) {
                events.addCalendarEntry(calendarEntry);
            } else {
                reminders.addCalendarEntry(calendarEntry);
            }
        }

        JsonUtil.saveJsonFile(new JsonSerializableEventList(events), eventFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableReminderList(reminders), reminderFilePath);
    }
}
