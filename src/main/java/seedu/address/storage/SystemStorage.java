package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Data;
import seedu.address.model.ReadOnlyData;
import seedu.address.model.competition.Competition;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * Represents a storage for {@link Data}.
 */
public interface SystemStorage {

    /**
     * Returns the file path of the person data file.
     */
    Path getPersonDataFilePath();

    /**
     * Returns the file path of the competition data file.
     */
    Path getCompetitionDataFilePath();

    /**
     * Returns the file path of the participation data file.
     */
    Path getParticipationDataFilePath();

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Person>> readPersonData(Path filePath) throws DataConversionException, IOException;

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Person>> readPersonData() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyData} to the storage.
     * @param readOnlyData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePersonData(ReadOnlyData<Person> readOnlyData) throws IOException;

    void savePersonData(ReadOnlyData<Person> readOnlyData, Path filePath) throws IOException;

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Competition>> readCompetitionData(Path filePath) throws DataConversionException, IOException;

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Competition>> readCompetitionData() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyData} to the storage.
     * @param readOnlyData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCompetitionData(ReadOnlyData<Competition> readOnlyData) throws IOException;

    /**
     * Saves the given {@link ReadOnlyData} to the storage.
     * @param readOnlyData cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCompetitionData(ReadOnlyData<Competition> readOnlyData, Path filePath) throws IOException;

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Participation>> readParticipationData(
        Path filePath,
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws DataConversionException, IOException;

    /**
     * Returns Data data as a {@link ReadOnlyData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyData<Participation>> readParticipationData(
        ReadOnlyData<Person> personReadOnlyData,
        ReadOnlyData<Competition> competitionReadOnlyData
    ) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyData} to the storage.
     * @param readOnlyData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveParticipationData(ReadOnlyData<Participation> readOnlyData) throws IOException;

    /**
     * Saves the given {@link ReadOnlyData} to the storage.
     * @param readOnlyData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveParticipationData(ReadOnlyData<Participation> readOnlyData, Path filePath) throws IOException;

}
