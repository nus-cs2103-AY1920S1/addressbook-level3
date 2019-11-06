package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.TutorAid;

/**
 * Represents a storage for {@link TutorAid}.
 */
public interface TutorAidStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTutorAidFilePath();

    /**
     * Returns TutorAid data as a {@link ReadOnlyTutorAid}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTutorAid> readTutorAid() throws DataConversionException, IOException;

    /**
     * @see #getTutorAidFilePath()
     */
    Optional<ReadOnlyTutorAid> readTutorAid(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTutorAid} to the storage.
     * @param tutorAid cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTutorAid(ReadOnlyTutorAid tutorAid) throws IOException;

    /**
     * @see #saveTutorAid(ReadOnlyTutorAid)
     */
    void saveTutorAid(ReadOnlyTutorAid tutorAid, Path filePath) throws IOException;

}
