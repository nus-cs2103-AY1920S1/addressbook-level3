package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.schedule.Schedule;

/**
 * A class to access Schedule DataBook data stored as a json file on the hard disk.
 */
public class JsonScheduleBookStorage implements ScheduleBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonScheduleBookStorage.class);

    private Path filePath;

    public JsonScheduleBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getScheduleBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDataBook<Schedule>> readScheduleBook() throws DataConversionException {
        return readScheduleBook(filePath);
    }

    /**
     * Similar to {@link #readScheduleBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDataBook<Schedule>> readScheduleBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableScheduleBook> jsonScheduleBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableScheduleBook.class);
        if (!jsonScheduleBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScheduleBook.get().toModelType());
        } catch (IllegalValueException | ParseException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) throws IOException {
        saveScheduleBook(scheduleBook, filePath);
    }

    /**
     * Similar to {@link #saveScheduleBook(ReadOnlyDataBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook, Path filePath) throws IOException {
        requireNonNull(scheduleBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleBook(scheduleBook), filePath);
    }

}
