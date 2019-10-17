package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.storage.note.NotesRecordStorage;
import seedu.address.storage.question.QuestionStorage;
import seedu.address.storage.student.StudentRecordStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private StudentRecordStorage studentRecordStorage;
    private QuestionStorage questionStorage;
    private NotesRecordStorage notesRecordStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
        StudentRecordStorage studentRecordStorage, QuestionStorage questionStorage, NotesRecordStorage notesStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.studentRecordStorage = studentRecordStorage;
        this.questionStorage = questionStorage;
        this.notesRecordStorage = notesStorage;
    }

    //region UserPrefs
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
    //endregion

    //region AddressBook methods
    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook()
        throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
        throws DataConversionException, IOException {
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
    //endregion

    //region StudentRecord methods
    @Override
    public Path getStudentRecordFilePath() {
        return studentRecordStorage.getStudentRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyStudentRecord> readStudentRecord()
        throws DataConversionException, IOException {
        return readStudentRecord(studentRecordStorage.getStudentRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentRecord> readStudentRecord(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read student data from file: " + filePath);
        return studentRecordStorage.readStudentRecord(filePath);
    }

    @Override
    public void saveStudentRecord(ReadOnlyStudentRecord studentRecord) throws IOException {
        saveStudentRecord(studentRecord, studentRecordStorage.getStudentRecordFilePath());
    }

    @Override
    public void saveStudentRecord(ReadOnlyStudentRecord studentRecord, Path filePath)
        throws IOException {
        logger.fine("Attempting to write to student data file: " + filePath);
        studentRecordStorage.saveStudentRecord(studentRecord, filePath);
    }
    //endregion

    //region Question methods
    @Override
    public Path getSavedQuestionsFilePath() {
        return questionStorage.getSavedQuestionsFilePath();
    }

    @Override
    public Optional<ReadOnlyQuestions> readQuestions() throws DataConversionException, IOException {
        return readQuestions(questionStorage.getSavedQuestionsFilePath());
    }

    @Override
    public Optional<ReadOnlyQuestions> readQuestions(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read question data from file: " + filePath);
        return questionStorage.readQuestions(filePath);
    }

    @Override
    public void saveQuestions(ReadOnlyQuestions questions) throws IOException {
        saveQuestions(questions, questionStorage.getSavedQuestionsFilePath());
    }

    @Override
    public void saveQuestions(ReadOnlyQuestions questions, Path filePath) throws IOException {
        logger.fine("Attempting to write to student data file: " + filePath);
        questionStorage.saveQuestions(questions, filePath);
    }
    //endregion

    //region NotesRecord methods
    @Override
    public Path getNotesRecordFilePath() {
        return notesRecordStorage.getNotesRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyNotesRecord> readNotesRecord()
            throws DataConversionException, IOException {
        return readNotesRecord(notesRecordStorage.getNotesRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyNotesRecord> readNotesRecord(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read student data from file: " + filePath);
        return notesRecordStorage.readNotesRecord(filePath);
    }

    @Override
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException {
        saveNotesRecord(notesRecord, notesRecordStorage.getNotesRecordFilePath());
    }

    @Override
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to student data file: " + filePath);
        notesRecordStorage.saveNotesRecord(notesRecord, filePath);
    }
    //endregion

}
