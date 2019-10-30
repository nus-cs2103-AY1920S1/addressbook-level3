package tagline.storage.tag;

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
import tagline.model.tag.ReadOnlyTagBook;

/**
 * A class to access UniqueTagBook data stored as a json file on the hard disk.
 */
public class JsonTagBookStorage implements TagBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTagBookStorage.class);

    private Path filePath;

    public JsonTagBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTagBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTagBook> readTagBook() throws DataConversionException {
        return readTagBook(filePath);
    }

    /**
     * Similar to {@link #readTagBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTagBook> readTagBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTagBook> jsonTagBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableTagBook.class);
        if (jsonTagBook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTagBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTagBook(ReadOnlyTagBook tagBook) throws IOException {
        saveTagBook(tagBook, filePath);
    }

    /**
     * Similar to {@link #saveTagBook(ReadOnlyTagBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTagBook(ReadOnlyTagBook tagBook, Path filePath) throws IOException {
        requireNonNull(tagBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTagBook(tagBook), filePath);
    }

}
