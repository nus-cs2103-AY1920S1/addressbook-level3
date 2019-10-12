package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PasswordBook;

/**
 * A class to access PasswordBook data stored as a json file on the hard disk.
 */
public class JsonPasswordBookStorage implements PasswordBookStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonPasswordBookStorage.class);

    private Path filePath;
    private String password;

    public JsonPasswordBookStorage(Path filePath, String password) {
        this.filePath = filePath;
        this.password = password;
    }

    @Override
    public Path getPasswordBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<PasswordBook> readPasswordBook() throws DataConversionException, IOException {
        return readPasswordBook(filePath);
    }

    @Override
    public Optional<PasswordBook> readPasswordBook(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath, password);

        Optional<JsonSerializablePasswordBook> jsonPasswordBook;
        jsonPasswordBook = JsonUtil.readEncryptedJsonFile(filePath, JsonSerializablePasswordBook.class, password);

        if (!jsonPasswordBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPasswordBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePasswordBook(PasswordBook passwordBook) throws IOException {
        savePasswordBook(passwordBook, filePath);
    }

    @Override
    public void savePasswordBook(PasswordBook passwordBook, Path filePath) throws IOException {
        requireAllNonNull(filePath, passwordBook, password);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveEncryptedJsonFile(new JsonSerializablePasswordBook(passwordBook), filePath, password);
    }
}
