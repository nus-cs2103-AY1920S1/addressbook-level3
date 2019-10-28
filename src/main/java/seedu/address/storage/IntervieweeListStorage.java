package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyIntervieweeList;

/**
 * Represents a storage for {@link seedu.address.model.IntervieweeList}
 */
public interface IntervieweeListStorage {

    /**
     * Returns the file path of the interviewees data file.
     */
    Path getIntervieweeListFilePath();

    /**
     * Returns Interviewees data as an {@link ReadOnlyIntervieweeList}
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyIntervieweeList> readIntervieweeList() throws DataConversionException, IOException;

    /**
     * @see #getIntervieweeListFilePath()
     */
    Optional<ReadOnlyIntervieweeList> readIntervieweeList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyIntervieweeList} to the storage.
     * @param intervieweeList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveIntervieweeList(ReadOnlyIntervieweeList intervieweeList) throws IOException;

    /**
     * @see #saveIntervieweeList(ReadOnlyIntervieweeList)
     */
    void saveIntervieweeList(ReadOnlyIntervieweeList intervieweeList, Path filePath) throws IOException;

}
