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
import seedu.address.model.ReadOnlyNoteBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonNoteBookStorage implements NoteBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNoteBookStorage.class);

    private Path filePath;
    private String password;

    public JsonNoteBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public JsonNoteBookStorage(Path filePath, String password) {
        this.filePath = filePath;
        this.password = password;
    }

    public Path getNoteBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException, IOException {
        return readNoteBook(filePath);
    }

    /**
     * Similar to {@link #readNoteBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyNoteBook> readNoteBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNoteBook> jsonNoteBook;
        if (password == null) {
            jsonNoteBook = JsonUtil.readJsonFile(filePath, JsonSerializableNoteBook.class);
        } else {
            jsonNoteBook = JsonUtil.readEncryptedJsonFile(filePath, JsonSerializableNoteBook.class, password);
        }
        if (!jsonNoteBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNoteBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNoteBook(ReadOnlyNoteBook noteBook) throws IOException {
        saveNoteBook(noteBook, filePath);
    }

    /**
     * Similar to {@link #saveNoteBook(ReadOnlyNoteBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveNoteBook(ReadOnlyNoteBook noteBook, Path filePath) throws IOException {
        requireNonNull(noteBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        if (password == null) {
            JsonUtil.saveJsonFile(new JsonSerializableNoteBook(noteBook), filePath);
        } else {
            JsonUtil.saveEncryptedJsonFile(new JsonSerializableNoteBook(noteBook), filePath, password);
        }
    }

}
