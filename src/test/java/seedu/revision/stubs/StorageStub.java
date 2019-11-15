package seedu.revision.stubs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.revision.commons.exceptions.DataConversionException;
import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.ReadOnlyUserPrefs;
import seedu.revision.model.UserPrefs;
import seedu.revision.storage.Storage;

/** Storage stub used for dependency injection.**/
public class StorageStub implements Storage {

    /** Initialises the storage stub.**/
    public StorageStub() {
        super();
    }

    @Override
    public Path getUserPrefsFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        //do nothing
    }

    @Override
    public Path getRevisionToolFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyRevisionTool> readRevisionTool() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyRevisionTool> readRevisionTool(Path filePath) throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool) throws IOException {
        //do nothing
    }

    @Override
    public void saveRevisionTool(ReadOnlyRevisionTool revisionTool, Path filePath) throws IOException {
        //do nothing
    }

    @Override
    public Path getHistoryFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyHistory> readHistory() throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyHistory> readHistory(Path filePath) throws DataConversionException, IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveHistory(ReadOnlyHistory history) throws IOException {
        //do nothing
    }

    @Override
    public void saveHistory(ReadOnlyHistory history, Path filePath) throws IOException {
        //do nothing
    }
}
