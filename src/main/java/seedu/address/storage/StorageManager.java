package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private IntervieweeListStorage intervieweeListStorage;
    private InterviewerListStorage interviewerListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(IntervieweeListStorage intervieweeListStorage,
                          InterviewerListStorage interviewerListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.intervieweeListStorage = intervieweeListStorage;
        this.interviewerListStorage = interviewerListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ IntervieweeList methods ==============================

    @Override
    public Path getIntervieweeListFilePath() {
        return this.intervieweeListStorage.getIntervieweeListFilePath();
    }

    @Override
    public Optional<ReadOnlyList<Interviewee>> readIntervieweeList() throws DataConversionException, IOException {
        return readIntervieweeList(this.intervieweeListStorage.getIntervieweeListFilePath());
    }

    @Override
    public Optional<ReadOnlyList<Interviewee>> readIntervieweeList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read interviewee data from file: " + filePath);
        return this.intervieweeListStorage.readIntervieweeList(filePath);
    }

    @Override
    public void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList) throws IOException {
        saveIntervieweeList(intervieweeList, this.intervieweeListStorage.getIntervieweeListFilePath());
    }

    @Override
    public void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList, Path filePath) throws IOException {
        logger.fine("Attempting to write to interviewee data file: " + filePath);
        this.intervieweeListStorage.saveIntervieweeList(intervieweeList, filePath);
    }

    // ================ InterviewerList methods ==============================

    @Override
    public Path getInterviewerListFilePath() {
        return this.interviewerListStorage.getInterviewerListFilePath();
    }

    @Override
    public Optional<ReadOnlyList<Interviewer>> readInterviewerList() throws DataConversionException, IOException {
        return readInterviewerList(this.interviewerListStorage.getInterviewerListFilePath());
    }

    @Override
    public Optional<ReadOnlyList<Interviewer>> readInterviewerList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read interviewer data from file: " + filePath);
        return this.interviewerListStorage.readInterviewerList(filePath);
    }

    @Override
    public void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList) throws IOException {
        saveInterviewerList(interviewerList, this.interviewerListStorage.getInterviewerListFilePath());
    }

    @Override
    public void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, Path filePath) throws IOException {
        logger.fine("Attempting to write to interviewer data file: " + filePath);
        this.interviewerListStorage.saveInterviewerList(interviewerList, filePath);
    }

}
