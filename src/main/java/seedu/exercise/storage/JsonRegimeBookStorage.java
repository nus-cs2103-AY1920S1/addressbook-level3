package seedu.exercise.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyRegimeBook;

/**
 * A class to access RegimeBook data stored as a json file on the hard disk.
 */
public class JsonRegimeBookStorage implements RegimeBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonRegimeBookStorage.class);

    private Path filePath;

    public JsonRegimeBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRegimeBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRegimeBook> readRegimeBook() throws DataConversionException {
        return readRegimeBook(filePath);
    }

    /**
     * Similar to {@link #readRegimeBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRegimeBook> readRegimeBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRegimeBook> jsonRegimeBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRegimeBook.class);
        if (!jsonRegimeBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRegimeBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRegimeBook(ReadOnlyRegimeBook regimeBook) throws IOException {
        saveRegimeBook(regimeBook, filePath);
    }

    /**
     * Similar to {@link #saveRegimeBook(ReadOnlyRegimeBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRegimeBook(ReadOnlyRegimeBook regimeBook, Path filePath) throws IOException {
        requireNonNull(regimeBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRegimeBook(regimeBook), filePath);
    }
}
