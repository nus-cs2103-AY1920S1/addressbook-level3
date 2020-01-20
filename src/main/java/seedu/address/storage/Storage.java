package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyProjectList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, ProjectListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException, ParseException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    //======= Project List =================================================================
    @Override
    Path getProjectListFilePath();

    @Override
    Path getBudgetsExcelFilePath();

    @Override
    Optional<ReadOnlyProjectList> readProjectList() throws DataConversionException, IOException;

    @Override
    void saveProjectList(ReadOnlyProjectList projectList) throws IOException;

    @Override
    void saveBudgetsToExcel(ReadOnlyProjectList projectList) throws IOException;

    @Override
    void saveBudgetsToExcel(ReadOnlyProjectList projectList, Path filePath) throws IOException;
}
