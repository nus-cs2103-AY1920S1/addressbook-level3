package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.AlfredException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.entitylist.TeamList;

/**
 * Represents a storage for {@link seedu.address.model.entitylist.TeamList}
 */
public interface TeamListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getTeamListFilePath();

    /**
     * Returns TeamList data as a {@link TeamList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<TeamList> readTeamList() throws DataConversionException, IOException, AlfredException;

    /**
     * @see #getTeamListFilePath()
     */
    Optional<TeamList> readTeamList(Path filePath) throws DataConversionException, IOException, AlfredException;

    /**
     * Saves the given {@link TeamList} to the storage.
     * @param teamList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTeamList(TeamList teamList) throws IOException;

    /**
     * @see #saveTeamList(TeamList)
     */
    void saveTeamList(TeamList teamList, Path filePath) throws IOException;
}

