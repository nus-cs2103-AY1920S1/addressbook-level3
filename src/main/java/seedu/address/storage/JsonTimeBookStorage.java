package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TimeBook;

/**
 * A class to access TimeBook data stored as a JSON file on the hard disk.
 */
public class JsonTimeBookStorage implements TimeBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTimeBookStorage.class);

    private Path filePath;

    public JsonTimeBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTimeBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<TimeBook> readTimeBook() throws DataConversionException, IOException {
        return readTimeBook(filePath);
    }

    @Override
    public Optional<TimeBook> readTimeBook(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableTimeBook> jsonTimeBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableTimeBook.class);
        if (!jsonTimeBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTimeBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }

    }

    @Override
    public void saveTimeBook(TimeBook timeBook) throws IOException {
        saveTimeBook(timeBook, filePath);
    }

    @Override
    public void saveTimeBook(TimeBook timeBook, Path filePath) throws IOException {
        requireNonNull(timeBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTimeBook(timeBook), filePath);

    }
}
