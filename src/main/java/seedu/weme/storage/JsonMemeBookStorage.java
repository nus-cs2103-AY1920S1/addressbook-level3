package seedu.weme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.FileUtil;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.ReadOnlyMemeBook;

/**
 * A class to access MemeBook data stored as a json file on the hard disk.
 */
public class JsonMemeBookStorage implements MemeBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMemeBookStorage.class);

    private Path filePath;

    public JsonMemeBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMemeBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMemeBook> readMemeBook() throws DataConversionException {
        return readMemeBook(filePath);
    }

    /**
     * Similar to {@link #readMemeBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMemeBook> readMemeBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMemeBook> jsonMemeBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableMemeBook.class);
        if (!jsonMemeBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMemeBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMemeBook(ReadOnlyMemeBook memeBook) throws IOException {
        saveMemeBook(memeBook, filePath);
    }

    /**
     * Similar to {@link #saveMemeBook(ReadOnlyMemeBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMemeBook(ReadOnlyMemeBook memeBook, Path filePath) throws IOException {
        requireNonNull(memeBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMemeBook(memeBook), filePath);
    }

}
