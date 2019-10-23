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
import seedu.exercise.model.property.PropertyBook;

/**
 * A class to access PropertyBook data stored as a json file in the hard disk.
 */
public class JsonPropertyBookStorage implements PropertyBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPropertyBookStorage.class);

    private Path filePath;

    public JsonPropertyBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPropertyBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<PropertyBook> readPropertyBook() throws DataConversionException {
        return readPropertyBook(filePath);
    }

    /**
     * Similar to {@link #readPropertyBook()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format
     */
    public Optional<PropertyBook> readPropertyBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePropertyBook> jsonPropertyBook =
            JsonUtil.readJsonFile(filePath, JsonSerializablePropertyBook.class);
        if (jsonPropertyBook.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonPropertyBook.get().toModelManager());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void savePropertyBook(PropertyBook propertyBook) throws IOException {
        savePropertyBook(propertyBook, filePath);
    }

    /**
     * Similar to {@link #savePropertyBook(PropertyBook)}
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePropertyBook(PropertyBook propertyBook, Path filePath) throws IOException {
        requireNonNull(propertyBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePropertyBook(propertyBook), filePath);
    }
}
