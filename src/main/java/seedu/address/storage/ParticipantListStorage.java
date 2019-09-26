package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AddressBook;

/**
 * Represents a storage for {@link seedu.address.model.EntityList.TeamList}
 */
public interface ParticipantListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getParticipantListFilePath();

    /**
     * Returns ParticipantList data as a {@link ParticipantList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ParticipantList> readParticipantList() throws DataConversionException, IOException;

    /**
     * @see #getParticipantListFilePath()
     */
    Optional<ParticipantList> readParticipantList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ParticipantList} to the storage.
     * @param participantList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveParticipantList(ParticipantList participantList) throws IOException;

    /**
     * @see #saveParticipantList(ParticipantList)
     */
    void saveParticipantList(ParticipantList participantList, Path filePath) throws IOException;
}

