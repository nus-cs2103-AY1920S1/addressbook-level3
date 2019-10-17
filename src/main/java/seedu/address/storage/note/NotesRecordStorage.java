package seedu.address.storage.note;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.note.ReadOnlyNotesRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.NotesRecord}.
 */
public interface NotesRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNotesRecordFilePath();

    /**
     * Returns NotesRecord data as a {@link ReadOnlyNotesRecord}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyNotesRecord> readNotesRecord() throws DataConversionException, IOException;

    /**
     * @see #getNotesRecordFilePath()
     */
    Optional<ReadOnlyNotesRecord> readNotesRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNotesRecord} to the storage.
     * @param notesRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException;

    /**
     * @see #saveNotesRecord(ReadOnlyNotesRecord)
     */
    void saveNotesRecord(ReadOnlyNotesRecord notesRecord, Path filePath) throws IOException;
}
