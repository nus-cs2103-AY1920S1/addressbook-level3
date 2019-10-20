package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.statistics.WordBankStatisticsStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, WordBankStatisticsStorage, AppSettingsStorage {

    @Override
    Optional<WordBankStatistics> readWordBankStatistics() throws DataConversionException, IOException;

    @Override
    void saveWordBankStatistics(WordBankStatistics wbStats) throws IOException;

    @Override
    Path getWordBankStatisticsFilePath();


    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyWordBank> readAddressBook() throws DataConversionException, IOException;

    /*
    Step 4.
    Define method signature of readGame here.
    Extends into concrete definition in StorageManager.java
     */

    @Override
    void saveAddressBook(ReadOnlyWordBank addressBook) throws IOException;


}
