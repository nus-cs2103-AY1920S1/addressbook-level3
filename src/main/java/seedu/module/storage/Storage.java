package seedu.module.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.ReadOnlyUserPrefs;
import seedu.module.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ModuleBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getModuleBookFilePath();

    @Override
    ReadOnlyModuleBook readModuleBook();

    @Override
    void saveModuleBook(ReadOnlyModuleBook moduleBook) throws IOException;

}
