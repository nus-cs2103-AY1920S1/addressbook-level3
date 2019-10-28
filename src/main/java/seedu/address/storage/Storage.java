package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyIntervieweeList;
import seedu.address.model.ReadOnlyInterviewerList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends IntervieweeListStorage, InterviewerListStorage, UserPrefsStorage {

    //=========== UserPrefs ============================================================================================
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //=========== IntervieweeList ======================================================================================

    @Override
    Path getIntervieweeListFilePath();

    @Override
    Optional<ReadOnlyIntervieweeList> readIntervieweeList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyIntervieweeList> readIntervieweeList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveIntervieweeList(ReadOnlyIntervieweeList intervieweeList) throws IOException;

    @Override
    void saveIntervieweeList(ReadOnlyIntervieweeList intervieweeList, Path filePath) throws IOException;

    //=========== InterviewerList ======================================================================================

    @Override
    Path getInterviewerListFilePath();

    @Override
    Optional<ReadOnlyInterviewerList> readInterviewerList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyInterviewerList> readInterviewerList(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveInterviewerList(ReadOnlyInterviewerList interviewerList) throws IOException;

    @Override
    void saveInterviewerList(ReadOnlyInterviewerList interviewerList, Path filePath) throws IOException;

}
