package seedu.ichifund.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ichifund.commons.exceptions.DataConversionException;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.ReadOnlyUserPrefs;
import seedu.ichifund.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FundBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFundBookFilePath();

    @Override
    Optional<ReadOnlyFundBook> readFundBook() throws DataConversionException, IOException;

    @Override
    void saveFundBook(ReadOnlyFundBook fundBook) throws IOException;

}
