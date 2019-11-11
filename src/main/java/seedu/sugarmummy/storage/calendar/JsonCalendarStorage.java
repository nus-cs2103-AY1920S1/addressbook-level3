package seedu.sugarmummy.storage.calendar;

import static seedu.sugarmummy.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.commons.exceptions.DataConversionException;
import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.commons.util.FileUtil;
import seedu.sugarmummy.commons.util.JsonUtil;
import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;

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
