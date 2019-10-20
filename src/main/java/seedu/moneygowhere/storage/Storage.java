package seedu.moneygowhere.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.ReadOnlyUserPrefs;
import seedu.moneygowhere.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SpendingBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getSpendingBookFilePath();

    @Override
    Optional<ReadOnlySpendingBook> readSpendingBook() throws DataConversionException, IOException;

    @Override
    void saveSpendingBook(ReadOnlySpendingBook spendingBook) throws IOException;

}
