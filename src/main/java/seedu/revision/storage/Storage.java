package seedu.revision.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RevisionToolStorage, UserPrefsStorage, HistoryStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getRevisionToolFilePath();

    @Override
    Optional<ReadOnlyRevisionTool> readRevisionTool() throws DataConversionException, IOException;

    @Override
    void saveRevisionTool(ReadOnlyRevisionTool revisionTool) throws IOException;

    @Override
    Path getHistoryFilePath();

    @Override
    Optional<ReadOnlyHistory> readHistory() throws DataConversionException, IOException;

    @Override
    void saveHistory(ReadOnlyHistory history) throws IOException;

}
