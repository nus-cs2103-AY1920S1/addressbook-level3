package seedu.mark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.mark.commons.exceptions.DataConversionException;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.ReadOnlyUserPrefs;
import seedu.mark.model.UserPrefs;

/**
 * A default Storage Stub that has all of its methods failing.
 */
public class StorageStub implements Storage {
    @Override
    public Path getUserPrefsFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getMarkFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyMark> readMark() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<ReadOnlyMark> readMark(Path filePath) throws IOException, DataConversionException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveMark(ReadOnlyMark mark) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveMark(ReadOnlyMark mark, Path filePath) {
        throw new AssertionError("This method should not be called.");
    }
}
