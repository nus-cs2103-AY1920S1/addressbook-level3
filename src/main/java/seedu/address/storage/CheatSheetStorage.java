package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCheatSheetBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of cheatSheetStorage component
 */

public interface CheatSheetStorage extends CheatSheetBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCheatSheetBookFilePath();

    @Override
    Optional<ReadOnlyCheatSheetBook> readCheatSheetBook() throws DataConversionException, IOException;

    @Override
    void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook) throws IOException;
}
