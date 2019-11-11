package seedu.address.calendar.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.calendar.model.ReadOnlyCalendar;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;

/**
 * Json representation of a calendar.
 */
public class JsonCalendarStorage implements CalendarStorage {
    private Path filePath;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public JsonCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the calendar.
     *
     * @return Returns a read only calendar, if any
     * @throws DataConversionException If the data cannot be converted successfully
     * @throws IOException If the file cannot be read to/read from
     */
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableCalendar> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableCalendar.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Calendar toModel: " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the specified calendar.
     *
     * @param calendar The specified calendar
     * @throws IOException If the data cannot be saved
     */
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        requireNonNull(calendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalendar(calendar), filePath);
    };
}
