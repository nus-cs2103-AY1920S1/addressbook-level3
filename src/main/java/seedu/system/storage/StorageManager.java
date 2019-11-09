package seedu.system.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.system.commons.core.LogsCenter;
import seedu.system.commons.exceptions.DataConversionException;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.ReadOnlyUserPrefs;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;

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
        logger.fine("Attempting to read user preferences from file: " + userPrefsStorage.getUserPrefsFilePath());
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        logger.fine("Attempting to save user preferences data to file:" + userPrefsStorage.getUserPrefsFilePath());
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Person Data methods ==============================

    @Override
    public Path getPersonDataFilePath() {
        return systemStorage.getPersonDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Person>> readPersonData() throws DataConversionException, IOException {
        logger.fine("Attempting to read person data from file: " + systemStorage.getPersonDataFilePath());
        return readPersonData(systemStorage.getPersonDataFilePath());
    }

    @Override
    public Optional<ReadOnlyData<Person>> readPersonData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read person data from file: " + filePath);
        return systemStorage.readPersonData(filePath);
    }

    @Override
    public void savePersonData(ReadOnlyData<Person> readOnlyData) throws IOException {
        logger.fine("Attempting to save person data to file:"
            + systemStorage.getPersonDataFilePath());
        systemStorage.savePersonData(readOnlyData, systemStorage.getPersonDataFilePath());
    }

    @Override
    public void savePersonData(ReadOnlyData<Person> readOnlyData, Path filePath) throws IOException {
        logger.fine("Attempting to save person data to file: " + filePath);
        systemStorage.savePersonData(readOnlyData, filePath);
    }

    // ================ Competition Data methods ==============================

    @Override
    public Path getCompetitionDataFilePath() {
        return systemStorage.getCompetitionDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Competition>> readCompetitionData() throws DataConversionException, IOException {
        logger.fine("Attempting to read competition data from file: "
            + systemStorage.getCompetitionDataFilePath());
        return readCompetitionData(systemStorage.getCompetitionDataFilePath());
    }

    @Override
    public Optional<ReadOnlyData<Competition>> readCompetitionData(
        Path filePath
    ) throws DataConversionException, IOException {
        logger.fine("Attempting to read competition data from file: " + filePath);
        return systemStorage.readCompetitionData(filePath);
    }

    @Override
    public void saveCompetitionData(ReadOnlyData<Competition> readOnlyData) throws IOException {
        logger.fine("Attempting to save competition data to file: "
            + systemStorage.getCompetitionDataFilePath());
        systemStorage.saveCompetitionData(readOnlyData, systemStorage.getCompetitionDataFilePath());
    }

    @Override
    public void saveCompetitionData(ReadOnlyData<Competition> readOnlyData, Path filePath) throws IOException {
        logger.fine("Attempting to save competition data to file: " + filePath);
        systemStorage.saveCompetitionData(readOnlyData, filePath);
    }

    // ================ Participation Data methods ==============================

    @Override
    public Path getParticipationDataFilePath() {
        return systemStorage.getParticipationDataFilePath();
    }

    @Override
    public Optional<ReadOnlyData<Participation>> readParticipationData(
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws DataConversionException, IOException {
        logger.fine("Attempting to read participation data from file: " + getParticipationDataFilePath());
        return readParticipationData(
            systemStorage.getParticipationDataFilePath(),
            personReadOnlyData,
            competitionReadOnlyData
        );
    }

    @Override
    public Optional<ReadOnlyData<Participation>> readParticipationData(
        Path filePath,
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws DataConversionException, IOException {
        logger.fine("Attempting to read participation data from file: " + filePath);
        return systemStorage.readParticipationData(filePath, personReadOnlyData, competitionReadOnlyData);
    }

    @Override
    public void saveParticipationData(ReadOnlyData<Participation> readOnlyData) throws IOException {
        logger.fine("Attempting to save participation data to file: "
            + systemStorage.getParticipationDataFilePath());
        systemStorage.saveParticipationData(readOnlyData, systemStorage.getParticipationDataFilePath());
    }

    @Override
    public void saveParticipationData(ReadOnlyData<Participation> readOnlyData, Path filePath) throws IOException {
        logger.fine("Attempting to save participation data to file: " + filePath);
        systemStorage.saveParticipationData(readOnlyData, filePath);
    }

}
