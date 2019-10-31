package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.person.Interviewer;

/**
 * Represents a storage for {@link seedu.address.model.InterviewerList}
 */
public interface InterviewerListStorage {

    /**
     * Returns the file path of the interviewers data file.
     */
    Path getInterviewerListFilePath();

    /**
     * Returns Interviewers data as an {@link ReadOnlyList}
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyList<Interviewer>> readInterviewerList() throws DataConversionException, IOException;

    /**
     * @see #getInterviewerListFilePath()
     */
    Optional<ReadOnlyList<Interviewer>> readInterviewerList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyList} to the storage.
     * @param interviewerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList) throws IOException;

    /**
     * @see #saveInterviewerList(ReadOnlyList)
     */
    void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, Path filePath) throws IOException;

}
