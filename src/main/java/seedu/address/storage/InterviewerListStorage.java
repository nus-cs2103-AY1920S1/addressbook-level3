package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyInterviewerList;

/**
 * Represents a storage for {@link seedu.address.model.InterviewerList}
 */
public interface InterviewerListStorage {

    /**
     * Returns the file path of the interviewers data file.
     */
    Path getInterviewerListFilePath();

    /**
     * Returns Interviewers data as an {@link ReadOnlyInterviewerList}
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInterviewerList> readInterviewerList() throws DataConversionException, IOException;

    /**
     * @see #getInterviewerListFilePath()
     */
    Optional<ReadOnlyInterviewerList> readInterviewerList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInterviewerList} to the storage.
     * @param interviewerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInterviewerList(ReadOnlyInterviewerList interviewerList) throws IOException;

    /**
     * @see #saveInterviewerList(ReadOnlyInterviewerList)
     */
    void saveInterviewerList(ReadOnlyInterviewerList interviewerList, Path filePath) throws IOException;

}
