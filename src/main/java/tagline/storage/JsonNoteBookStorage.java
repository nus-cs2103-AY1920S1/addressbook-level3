package tagline.storage;

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
import tagline.model.note.ReadOnlyNoteBook;

/**
 * A class to access NoteBook data stored as a json file on the hard disk.
 */
public class JsonNoteBookStorage implements NoteBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNoteBookStorage.class);

    private Path filePath;

    public JsonNoteBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getNoteBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException {
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

        Optional<JsonSerializableNoteBook> jsonNoteBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableNoteBook.class);
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
        JsonUtil.saveJsonFile(new JsonSerializableNoteBook(noteBook), filePath);
    }

}
