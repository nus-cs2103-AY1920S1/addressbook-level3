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
import seedu.address.model.ReadOnlyFileBook;

/**
 * A class to access FileBook data stored as a json file on the hard disk.
 */
public class JsonFileBookStorage implements FileBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFileBookStorage.class);

    private Path filePath;
    private String password;

    public JsonFileBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public JsonFileBookStorage(Path filePath, String password) {
        this.filePath = filePath;
        this.password = password;
    }

    public String getStoragePassword() {
        return password;
    }

    public Path getFileBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFileBook> readFileBook() throws DataConversionException {
        return readFileBook(filePath);
    }

    /**
     * Similar to {@link #readFileBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFileBook> readFileBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFileBook> jsonFileBook;
        if (password == null) {
            jsonFileBook = JsonUtil.readJsonFile(filePath, JsonSerializableFileBook.class);
        } else {
            jsonFileBook = JsonUtil.readEncryptedJsonFile(filePath, JsonSerializableFileBook.class, password);
        }
        if (!jsonFileBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFileBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFileBook(ReadOnlyFileBook fileBook) throws IOException {
        saveFileBook(fileBook, filePath);
    }

    /**
     * Similar to {@link #saveFileBook(ReadOnlyFileBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFileBook(ReadOnlyFileBook fileBook, Path filePath) throws IOException {
        requireNonNull(fileBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        if (password == null) {
            JsonUtil.saveJsonFile(new JsonSerializableFileBook(fileBook), filePath);
        } else {
            JsonUtil.saveEncryptedJsonFile(new JsonSerializableFileBook(fileBook), filePath, password);
        }
    }

}

