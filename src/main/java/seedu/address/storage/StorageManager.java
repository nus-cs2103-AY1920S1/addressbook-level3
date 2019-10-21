package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.event.EventStorage;
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
    private UserPrefsStorage userPrefsStorage;
    private EventStorage eventStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
        StudentRecordStorage studentRecordStorage, QuestionStorage questionStorage, EventStorage eventStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.studentRecordStorage = studentRecordStorage;
        this.questionStorage = questionStorage;
        this.eventStorage = eventStorage;
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

    // ================ StudentRecord methods ==============================

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

    // ================ Question methods ==============================

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
        logger.fine("Attempting to write to questions data file: " + filePath);
        questionStorage.saveQuestions(questions, filePath);
    }

    // ================ Event methods ==============================

    @Override
    public Path getEventRecordFilePath() {
        return eventStorage.getEventRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException {
        return readEvents(eventStorage.getEventRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read events data from file: " + filePath);
        return eventStorage.readEvents(filePath);
    }

    @Override
    public void saveEvents(ReadOnlyEvents events) throws IOException {
        saveEvents(events, eventStorage.getEventRecordFilePath());
    }

    @Override
    public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {
        logger.fine("Attempting to write to events data file: " + filePath);
        eventStorage.saveEvents(events, filePath);
    }

}
