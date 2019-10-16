package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyNoteBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.NoteBook}.
 */
public interface NoteBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNoteBookFilePath();

    /**
     * Returns NoteBook data as a {@link ReadOnlyNoteBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException, IOException;

    /**
     * @see #getNoteBookFilePath()
     */
    Optional<ReadOnlyNoteBook> readNoteBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNoteBook} to the storage.
     * @param noteBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNoteBook(ReadOnlyNoteBook noteBook) throws IOException;

    /**
     * @see #saveNoteBook(ReadOnlyNoteBook)
     */
    void saveNoteBook(ReadOnlyNoteBook noteBook, Path filePath) throws IOException;

}
