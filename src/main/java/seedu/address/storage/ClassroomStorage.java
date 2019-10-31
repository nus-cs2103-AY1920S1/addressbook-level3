package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ReadOnlyClassroom;

/**
 * Represents a storage for {@link Classroom}.
 */
public interface ClassroomStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClassroomFilePath();

    /**
     * Returns Classroom data as a {@link ReadOnlyClassroom}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyClassroom> readClassroom() throws DataConversionException, IOException;

    /**
     * @see #getClassroomFilePath()
     */
    Optional<ReadOnlyClassroom> readClassroom(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyClassroom} to the storage.
     * @param classroom cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClassroom(ReadOnlyClassroom classroom) throws IOException;

    /**
     * @see #saveClassroom(ReadOnlyClassroom)
     */
    void saveClassroom(ReadOnlyClassroom classroom, Path filePath) throws IOException;

}
