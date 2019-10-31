package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;

/**
 * API of the Storage component
 */
public interface Storage extends IntervieweeListStorage, InterviewerListStorage, UserPrefsStorage {

    //=========== UserPrefs ===========================================================================================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //=========== IntervieweeList =====================================================================================

    @Override
    Path getIntervieweeListFilePath();

    @Override
    Optional<ReadOnlyList<Interviewee>> readIntervieweeList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyList<Interviewee>> readIntervieweeList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList) throws IOException;

    @Override
    void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList, Path filePath) throws IOException;

    //=========== InterviewerList ======================================================================================

    @Override
    Path getInterviewerListFilePath();

    @Override
    Optional<ReadOnlyList<Interviewer>> readInterviewerList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyList<Interviewer>> readInterviewerList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList) throws IOException;

    @Override
    void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, Path filePath) throws IOException;

}
