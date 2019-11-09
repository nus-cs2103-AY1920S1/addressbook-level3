package cs.f10.t1.nursetraverse.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import cs.f10.t1.nursetraverse.commons.exceptions.DataConversionException;
import cs.f10.t1.nursetraverse.model.ReadOnlyAppointmentBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyUserPrefs;
import cs.f10.t1.nursetraverse.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PatientBookStorage, UserPrefsStorage, AppointmentBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPatientBookFilePath();

    @Override
    Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException, IOException;

    @Override
    void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException;

    @Override
    Path getAppointmentBookFilePath();

    @Override
    Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException, IOException;

    @Override
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

}
