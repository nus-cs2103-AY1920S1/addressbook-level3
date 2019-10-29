package seedu.elisa.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.elisa.commons.exceptions.DataConversionException;
import seedu.elisa.model.ReadOnlyUserPrefs;
import seedu.elisa.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ItemListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getItemListFilePath();

}
