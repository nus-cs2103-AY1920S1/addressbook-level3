package cs.f10.t1.nursetraverse.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.commons.exceptions.DataConversionException;
import cs.f10.t1.nursetraverse.model.ReadOnlyAppointmentBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyUserPrefs;
import cs.f10.t1.nursetraverse.model.UserPrefs;

/**
 * Manages storage of PatientBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PatientBookStorage patientBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private AppointmentBookStorage appointmentBookStorage;


    public StorageManager(PatientBookStorage patientBookStorage, UserPrefsStorage userPrefsStorage,
                          AppointmentBookStorage appointmentBookStorage) {
        super();
        this.patientBookStorage = patientBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.appointmentBookStorage = appointmentBookStorage;
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


    // ================ PatientBook methods ==============================

    @Override
    public Path getPatientBookFilePath() {
        return patientBookStorage.getPatientBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException, IOException {
        return readPatientBook(patientBookStorage.getPatientBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPatientBook> readPatientBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return patientBookStorage.readPatientBook(filePath);
    }

    @Override
    public void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException {
        savePatientBook(patientBook, patientBookStorage.getPatientBookFilePath());
    }

    @Override
    public void savePatientBook(ReadOnlyPatientBook patientBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        patientBookStorage.savePatientBook(patientBook, filePath);
    }

    // ================ AppointmentBook methods ==============================

    @Override
    public Path getAppointmentBookFilePath() {
        return appointmentBookStorage.getAppointmentBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException, IOException {
        return readAppointmentBook(appointmentBookStorage.getAppointmentBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath) throws DataConversionException,
                                                                                                    IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return appointmentBookStorage.readAppointmentBook(filePath);
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        saveAppointmentBook(appointmentBook, appointmentBookStorage.getAppointmentBookFilePath());
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        appointmentBookStorage.saveAppointmentBook(appointmentBook, filePath);
    }

}
