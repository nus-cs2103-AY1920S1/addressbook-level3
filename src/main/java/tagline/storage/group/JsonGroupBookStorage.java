//@@author e0031374
package tagline.storage.group;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tagline.commons.core.LogsCenter;
import tagline.commons.exceptions.DataConversionException;
import tagline.commons.exceptions.IllegalValueException;
import tagline.commons.util.FileUtil;
import tagline.commons.util.JsonUtil;
import tagline.model.group.ReadOnlyGroupBook;

/**
 * A class to access GroupBook data stored as a json file on the hard disk.
 */
public class JsonGroupBookStorage implements GroupBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGroupBookStorage.class);

    private Path filePath;

    public JsonGroupBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGroupBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGroupBook> readGroupBook() throws DataConversionException {
        return readGroupBook(filePath);
    }

    /**
     * Similar to {@link #readGroupBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGroupBook> readGroupBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGroupBook> jsonGroupBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableGroupBook.class);
        if (!jsonGroupBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGroupBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGroupBook(ReadOnlyGroupBook groupBook) throws IOException {
        saveGroupBook(groupBook, filePath);
    }

    /**
     * Similar to {@link #saveGroupBook(ReadOnlyGroupBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGroupBook(ReadOnlyGroupBook groupBook, Path filePath) throws IOException {
        requireNonNull(groupBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGroupBook(groupBook), filePath);
    }

}
