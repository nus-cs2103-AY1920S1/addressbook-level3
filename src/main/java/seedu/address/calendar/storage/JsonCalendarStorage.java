package seedu.address.calendar.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.calendar.model.ReadOnlyCalendar;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class JsonCalendarStorage implements CalendarStorage {
    private Path filePath;
    private final Logger logger = LogsCenter.getLogger(getClass());

    public JsonCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }

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

    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        requireNonNull(calendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalendar(calendar), filePath);
    };
}
