package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.AlfredException;
import seedu.address.model.entitylist.IssueList;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link seedu.address.model.entitylist.IssueList}
 */
public interface IssueListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getIssueListFilePath();

    /**
     * Returns IssueList data as a {@link IssueList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<IssueList> readIssueList() throws DataConversionException, IOException, AlfredException;

    /**
     * @see #getIssueListFilePath()
     */
    Optional<IssueList> readIssueList(Path filePath) throws DataConversionException, IOException, AlfredException;

    /**
     * Saves the given {@link IssueList} to the storage.
     * @param issueList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveIssueList(IssueList issueList) throws IOException;

    /**
     * @see #saveIssueList(IssueList)
     */
    void saveIssueList(IssueList issueList, Path filePath) throws IOException;
}

