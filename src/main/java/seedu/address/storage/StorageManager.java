package seedu.address.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyProjectList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private ProjectListStorage projectListStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          ProjectListStorage projectListStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.projectListStorage = projectListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException, ParseException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException, ParseException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    public static String copyImageToData(File file, String personName) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getPath());
            String destPath = "data/" + personName.replaceAll(" ", "_") + "." + fileExtension;

            byte[] fileContent = FileUtils.readFileToByteArray(file);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            File outputFile = new File(destPath);
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
            return destPath;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // ================ ProjectList methods ==============================

    @Override
    public Path getProjectListFilePath() {
        return projectListStorage.getProjectListFilePath();
    }

    @Override
    public Path getBudgetsExcelFilePath() {
        return projectListStorage.getBudgetsExcelFilePath();
    }

    @Override
    public Optional<ReadOnlyProjectList> readProjectList() throws DataConversionException, IOException {
        return readProjectList(projectListStorage.getProjectListFilePath());
    }

    @Override
    public Optional<ReadOnlyProjectList> readProjectList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return projectListStorage.readProjectList(filePath);
    }

    @Override
    public void saveProjectList(ReadOnlyProjectList projectList) throws IOException {
        saveProjectList(projectList, projectListStorage.getProjectListFilePath());
    }

    @Override
    public void saveProjectList(ReadOnlyProjectList projectList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        projectListStorage.saveProjectList(projectList, filePath);
    }

    @Override
    public void saveBudgetsToExcel(ReadOnlyProjectList projectList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        projectListStorage.saveBudgetsToExcel(projectList, filePath);
    }

    @Override
    public void saveBudgetsToExcel(ReadOnlyProjectList projectList) throws IOException {
        saveBudgetsToExcel(projectList, projectListStorage.getBudgetsExcelFilePath());
    }

}
