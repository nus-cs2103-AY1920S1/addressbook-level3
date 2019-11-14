package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * Manages storage of Entity (Participant, Mentor, Team) and UserPref data in local storage.
 */
public class AlfredStorageManager implements AlfredStorage {

    private static final Logger logger = LogsCenter.getLogger(AlfredStorageManager.class);
    private ParticipantListStorage pStore;
    private MentorListStorage mStore;
    private TeamListStorage tStore;
    private UserPrefsStorage userPrefsStorage;

    public AlfredStorageManager(ParticipantListStorage pStore,
                                MentorListStorage mStore,
                                TeamListStorage tStore,
                                UserPrefsStorage userPrefsStorage) {
        super();
        this.pStore = pStore;
        this.mStore = mStore;
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
    public Optional<ParticipantList> readParticipantList() throws DataConversionException {
        return readParticipantList(pStore.getParticipantListFilePath());
    }

    @Override
    public Optional<ParticipantList> readParticipantList(Path filePath) throws DataConversionException {
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
    public Optional<MentorList> readMentorList() throws DataConversionException {
        return readMentorList(mStore.getMentorListFilePath());
    }

    @Override
    public Optional<MentorList> readMentorList(Path filePath) throws DataConversionException {
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

    // ================ TeamList methods ==============================
    @Override
    public Path getTeamListFilePath() {
        return tStore.getTeamListFilePath();
    }

    @Override
    public Optional<TeamList> readTeamList() throws DataConversionException {
        return readTeamList(tStore.getTeamListFilePath());
    }

    @Override
    public Optional<TeamList> readTeamList(Path filePath) throws DataConversionException {
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
