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
import seedu.address.model.ReadOnlyCardBook;

/**
 * A class to access CardBook data stored as a json file on the hard disk.
 */
public class JsonCardBookStorage implements CardBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCardBookStorage.class);

    private Path filePath;
    private String password;

    public JsonCardBookStorage(Path filePath, String password) {
        this.filePath = filePath;
        this.password = password;
    }

    public Path getCardBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCardBook> readCardBook() throws DataConversionException {
        return readCardBook(filePath);
    }

    /**
     * Similar to {@link #readCardBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCardBook> readCardBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCardBook> jsonCardBook;
        if (password == null) {
            jsonCardBook = JsonUtil.readJsonFile(filePath, JsonSerializableCardBook.class);
        } else {
            jsonCardBook = JsonUtil.readEncryptedJsonFile(filePath, JsonSerializableCardBook.class, password);
        }
        if (!jsonCardBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCardBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCardBook(ReadOnlyCardBook cardBook) throws IOException {
        saveCardBook(cardBook, filePath);
    }

    /**
     * Similar to {@link #saveCardBook(ReadOnlyCardBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCardBook(ReadOnlyCardBook cardBook, Path filePath) throws IOException {
        requireNonNull(cardBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        if (password == null) {
            JsonUtil.saveJsonFile(new JsonSerializableCardBook(cardBook), filePath);
        } else {
            JsonUtil.saveEncryptedJsonFile(new JsonSerializableCardBook(cardBook), filePath, password);
        }
    }

}
