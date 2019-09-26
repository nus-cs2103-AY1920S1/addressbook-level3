package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Entity (Participant, Mentor, Issue, Team) and UserPref data in local storage.
 */
public class AlfredStorageManager implements AlfredStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ParticipantListStorage pStore;
    private MentorListStorage mStore;
    private IssueListStorage iStore;
    private TeamListStorage tStore;
    private UserPrefsStorage userPrefsStorage;


    public AlfredStorageManager(ParticipantListStorage pStore,
                                MentorListStorage mStore,
                                IssueListStorage iStore,
                                TeamListStorage tStore,
                                UserPrefsStorage userPrefsStorage) {
        super();
        this.pStore = pStore;
        this.mStore = mStore;
        this.iStore = iStore;
        this.tStore = tStore;
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

    // ================ ParticipantList methods ==============================
    @Override
    public Path getParticipantListFilePath() {
        return pStore.getParticipantListFilePath();
    }

    @Override
    public Optional<ParticipantList> readParticipantList() throws DataConversionException, IOException {
        return readParticipantList(pStore.getParticipantListFilePath());
    }

    @Override
    public Optional<ParticipantList> readParticipantList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pStore.readParticipantList(filePath);
    }

    @Override
    public void saveParticipantList(ParticipantList pList) throws IOException {
        saveParticipantList(pList, pStore.getParticipantListFilePath());
    }

    @Override
    public void saveParticipantList(ParticipantList pList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pStore.saveParticipantList(pList, filePath);
    }

    // ================ MentorList methods ==============================
    @Override
    public Path getMentorListFilePath() {
        return mStore.getMentorListFilePath();
    }

    @Override
    public Optional<MentorList> readMentorList() throws DataConversionException, IOException {
        return readMentorList(mStore.getMentorListFilePath());
    }

    @Override
    public Optional<MentorList> readMentorList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return mStore.readMentorList(filePath);
    }

    @Override
    public void saveMentorList(MentorList mList) throws IOException {
        saveMentorList(mList, mStore.getMentorListFilePath());
    }

    @Override
    public void saveMentorList(MentorList mList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        mStore.saveMentorList(mList, filePath);
    }

    // ================ IssueList methods ==============================
    @Override
    public Path getIssueListFilePath() {
        return iStore.getIssueListFilePath();
    }

    @Override
    public Optional<IssueList> readIssueList() throws DataConversionException, IOException {
        return readIssueList(iStore.getIssueListFilePath());
    }

    @Override
    public Optional<IssueList> readIssueList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return iStore.readIssueList(filePath);
    }

    @Override
    public void saveIssueList(IssueList iList) throws IOException {
        saveIssueList(iList, iStore.getIssueListFilePath());
    }

    @Override
    public void saveIssueList(IssueList iList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        iStore.saveIssueList(iList, filePath);
    }

    // ================ TeamList methods ==============================
    @Override
    public Path getTeamListFilePath() {
        return tStore.getTeamListFilePath();
    }

    @Override
    public Optional<TeamList> readTeamList() throws DataConversionException, IOException {
        return readTeamList(tStore.getTeamListFilePath());
    }

    @Override
    public Optional<TeamList> readTeamList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tStore.readTeamList(filePath);
    }

    @Override
    public void saveTeamList(TeamList tList) throws IOException {
        saveTeamList(tList, tStore.getTeamListFilePath());
    }

    @Override
    public void saveTeamList(TeamList tList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tStore.saveTeamList(tList, filePath);
    }

}
