package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudentRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.StudentRecord}.
 */
public interface StudentRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStudentRecordFilePath();

    /**
     * Returns StudentRecord data as a {@link ReadOnlyStudentRecord}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudentRecord> readStudentRecord() throws DataConversionException, IOException;

    /**
     * @see #getStudentRecordFilePath()
     */
    Optional<ReadOnlyStudentRecord> readStudentRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudentRecord} to the storage.
     * @param studentRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudentRecord(ReadOnlyStudentRecord studentRecord) throws IOException;

    /**
     * @see #saveStudentRecord(ReadOnlyStudentRecord)
     */
    void saveStudentRecord(ReadOnlyStudentRecord studentRecord, Path filePath) throws IOException;

}
