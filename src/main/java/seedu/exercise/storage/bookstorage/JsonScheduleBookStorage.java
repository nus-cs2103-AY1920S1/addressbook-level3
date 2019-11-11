package seedu.exercise.storage.bookstorage;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.storage.serializablebook.JsonSerializableScheduleBook;

/**
 * A class to access ScheduleBook data stored as a json file on the hard disk.
 */
public class JsonScheduleBookStorage implements ResourceBookStorage<Schedule> {

    private static final Logger logger = LogsCenter.getLogger(JsonScheduleBookStorage.class);

    private Path filePath;

    public JsonScheduleBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getResourceBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyResourceBook<Schedule>> readResourceBook() throws DataConversionException {
        return readResourceBook(filePath);
    }

    @Override
    public Optional<ReadOnlyResourceBook<Schedule>> readResourceBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableScheduleBook> jsonScheduleBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableScheduleBook.class);

        if (!jsonScheduleBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScheduleBook.get().toModelType(Schedule.class, DEFAULT_SCHEDULE_COMPARATOR));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Schedule> scheduleBook) throws IOException {
        saveResourceBook(scheduleBook, filePath);
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Schedule> scheduleBook, Path filePath) throws IOException {
        requireAllNonNull(scheduleBook, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleBook(scheduleBook), filePath);
    }
}
