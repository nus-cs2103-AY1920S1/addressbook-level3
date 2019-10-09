package seedu.savenus.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MenuStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMenuFilePath();

    @Override
    Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException;

    @Override
    void saveMenu(ReadOnlyMenu menu) throws IOException;

}
