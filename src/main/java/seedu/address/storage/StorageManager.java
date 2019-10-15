package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyData;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.competition.Competition;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * Manages storage of Data data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SystemStorage systemStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SystemStorage systemStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.systemStorage = systemStorage;
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


    // ================ Person Data methods ==============================

    @Override
    public Path getPersonDataFilePath() {
        return systemStorage.getPersonDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Person>> readPersonData() throws DataConversionException, IOException {
        return readPersonData(systemStorage.getPersonDataFilePath());
    }

    @Override
    public Optional<ReadOnlyData<Person>> readPersonData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return systemStorage.readPersonData(filePath);
    }

    @Override
    public void savePersonData(ReadOnlyData<Person> readOnlyData) throws IOException {
        systemStorage.savePersonData(readOnlyData, systemStorage.getPersonDataFilePath());
    }

    @Override
    public void savePersonData(ReadOnlyData<Person> readOnlyData, Path filePath) throws IOException {
        systemStorage.savePersonData(readOnlyData, filePath);
    }

    // ================ Competition Data methods ==============================

    @Override
    public Path getCompetitionDataFilePath() {
        return systemStorage.getCompetitionDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Competition>> readCompetitionData() throws DataConversionException, IOException {
        return readCompetitionData(systemStorage.getCompetitionDataFilePath());
    }

    @Override
    public Optional<ReadOnlyData<Competition>> readCompetitionData(
        Path filePath
    ) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return systemStorage.readCompetitionData(filePath);
    }

    @Override
    public void saveCompetitionData(ReadOnlyData<Competition> readOnlyData) throws IOException {
        systemStorage.saveCompetitionData(readOnlyData, systemStorage.getCompetitionDataFilePath());
    }

    @Override
    public void saveCompetitionData(ReadOnlyData<Competition> readOnlyData, Path filePath) throws IOException {
        systemStorage.saveCompetitionData(readOnlyData, filePath);
    }

    // ================ Participation Data methods ==============================

    @Override
    public Path getParticipationDataFilePath() {
        return systemStorage.getParticipationDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Participation>> readParticipationData() throws DataConversionException, IOException {
        return readParticipationData(systemStorage.getParticipationDataFilePath());
    }

    @Override
    public Optional<ReadOnlyData<Participation>> readParticipationData(
        Path filePath
    ) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return systemStorage.readParticipationData(filePath);
    }

    @Override
    public void saveParticipationData(ReadOnlyData<Participation> readOnlyData) throws IOException {
        systemStorage.saveParticipationData(readOnlyData, systemStorage.getParticipationDataFilePath());
    }

    @Override
    public void saveParticipationData(ReadOnlyData<Participation> readOnlyData, Path filePath) throws IOException {
        systemStorage.saveParticipationData(readOnlyData, filePath);
    }

}
