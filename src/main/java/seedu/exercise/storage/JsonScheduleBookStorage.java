package seedu.exercise.storage;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyScheduleBook;

/**
 * A class to access ScheduleBook data stored as a json file on the hard disk.
 */
public class JsonScheduleBookStorage implements ScheduleBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonScheduleBookStorage.class);

    private Path filePath;

    public JsonScheduleBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getScheduleBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyScheduleBook> readScheduleBook() throws DataConversionException, IOException {
        return readScheduleBook(filePath);
    }

    @Override
    public Optional<ReadOnlyScheduleBook> readScheduleBook(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableScheduleBook> jsonScheduleBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableScheduleBook.class);

        if (!jsonScheduleBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScheduleBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveScheduleBook(ReadOnlyScheduleBook scheduleBook) throws IOException {
        saveScheduleBook(scheduleBook, filePath);
    }

    @Override
    public void saveScheduleBook(ReadOnlyScheduleBook scheduleBook, Path filePath) throws IOException {
        requireAllNonNull(scheduleBook, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleBook(scheduleBook), filePath);
    }
}
