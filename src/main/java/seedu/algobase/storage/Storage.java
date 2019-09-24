package seedu.algobase.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.algobase.commons.exceptions.DataConversionException;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AlgoBaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAlgoBaseFilePath();

    @Override
    Optional<ReadOnlyAlgoBase> readAlgoBase() throws DataConversionException, IOException;

    @Override
    void saveAlgoBase(ReadOnlyAlgoBase algoBase) throws IOException;

}
