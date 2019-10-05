package seedu.address.storage;

import java.util.Optional;
import java.io.IOException;
import java.nio.file.Path;

import seedu.address.AlfredException;
import seedu.address.model.entitylist.MentorList;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link seedu.address.model.entitylist.MentorList}
 */
public interface MentorListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getMentorListFilePath();

    /**
     * Returns MentorList data as a {@link MentorList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<MentorList> readMentorList() throws DataConversionException, IOException, AlfredException;

    /**
     * @see #getMentorListFilePath()
     */
    Optional<MentorList> readMentorList(Path filePath) throws DataConversionException, IOException, AlfredException;

    /**
     * Saves the given {@link MentorList} to the storage.
     * @param mentorList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMentorList(MentorList mentorList) throws IOException;

    /**
     * @see #saveMentorList(MentorList)
     */
    void saveMentorList(MentorList mentorList, Path filePath) throws IOException;
}

