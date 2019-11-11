package cs.f10.t1.nursetraverse.importexport;

import static cs.f10.t1.nursetraverse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.commons.util.FileUtil;
import cs.f10.t1.nursetraverse.importexport.exceptions.ExportingException;
import cs.f10.t1.nursetraverse.importexport.exceptions.ImportingException;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.storage.JsonAdaptedPatient;

/**
 * Reads and writes Java based Patient objects to and fro .csv files
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    private static final String MESSAGE_OVERRIDING_FORBIDDEN = "File with given filename already exists,"
            + "overriding is not allowed";
    private static final String MESSAGE_MISSING_FILE = "File not found.";

    //=========== Writing/Export functions =============================================================

    /**
     * Writes a list of Patients into a .csv file
     * @param patients pathString
     * @throws IOException
     */
    public static void writePatientsToCsv(List<Patient> patients, String pathString)
            throws IOException, ExportingException {
        requireAllNonNull(patients, pathString);
        assert !patients.isEmpty();

        Path writePath = Paths.get(pathString);
        if (FileUtil.isFileExists(writePath)) {
            throw new ExportingException(MESSAGE_OVERRIDING_FORBIDDEN);
        }
        assert !patients.isEmpty();
        String toWrite = getCsvStringFromPatients(patients);
        FileUtil.createFile(writePath);
        logger.info("Writing export data to: " + writePath);
        FileUtil.writeToFile(writePath, toWrite);
    }

    /**
     * Converts a List of patient objects into a String to be written into a .csv file
     * @param patients a list of patients
     * @return csv friendly string
     * @throws JsonProcessingException
     */
    public static String getCsvStringFromPatients(List<Patient> patients) throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .addMixIn(JsonAdaptedPatient.class, PatientMixIn.class);
        CsvSchema schema = mapper.schemaFor(JsonAdaptedPatient.class).withHeader().withArrayElementSeparator("\n");
        ObjectWriter writer = mapper.writer(schema);
        return writer.writeValueAsString(convertToCsvAdaptedList(patients));
    }

    /**
     * Converts a list of Patient objects to a list of Jackson .csv friendly objects
     * @param patients a list of json adapted patients
     * @return csv adapted patients
     */
    public static List<JsonAdaptedPatient> convertToCsvAdaptedList(List<Patient> patients) {
        requireNonNull(patients);
        List<JsonAdaptedPatient> csvAdaptedList = new ArrayList<>();
        for (Patient patient : patients) {
            csvAdaptedList.add(convertToCsvAdaptedPatient(patient));
        }
        return csvAdaptedList;
    }

    /**
     * Converts a Patient object to a Jackson .csv friendly object
     * @param patient
     * @return JsonAdaptedPatient
     */
    public static JsonAdaptedPatient convertToCsvAdaptedPatient(Patient patient) {
        requireNonNull(patient);
        return new JsonAdaptedPatient(patient);
    }

    //=========== Reading/Import functions ===============================================================

    /**
     * Reads data from a .csv file and converts it to a list of {@Code Patient} objects
     * @param pathString the string of the path to read from
     * @return a list of patients
     * @throws IOException from file util
     * @throws IllegalValueException if illegal values exist in the .csv file
     */

    public static List<Patient> readPatientsFromCsv(String pathString)
            throws IOException, IllegalValueException, ImportingException {
        requireNonNull(pathString);
        Path readPath = Paths.get(pathString);
        if (!FileUtil.isFileExists(readPath)) {
            throw new ImportingException(MESSAGE_MISSING_FILE);
        }

        CsvMapper mapper = new CsvMapper();
        mapper.addMixIn(JsonAdaptedPatient.class, PatientMixIn.class)
                // when an empty field is encountered, create a null object
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        CsvSchema schema = mapper.schemaFor(JsonAdaptedPatient.class)
                    .withHeader()
                    // list elements are separated by a new line
                    .withArrayElementSeparator("\n");
        MappingIterator<JsonAdaptedPatient> iter = mapper.readerFor(JsonAdaptedPatient.class)
                    .with(schema)
                    .readValues(readPath.toFile());
        List<JsonAdaptedPatient> importedCsvPatients = iter.readAll();

        return convertImportedPatientsToPatientList(importedCsvPatients);
    }

    /**
     * Converts a {@Code JsonAdaptedPatient} read from a csv file into a {@Code Patient} object
     */
    private static Patient convertToPatient(JsonAdaptedPatient patient) throws IllegalValueException {
        return patient.toModelType();
    }

    /**
     * Converts a list of {@Code JsonAdaptedPatient} objects read from a csv file
     * into a list of {@Code Patient} objects
     */
    private static List<Patient> convertImportedPatientsToPatientList(List<JsonAdaptedPatient> patients)
            throws IllegalValueException {
        List<Patient> newPatientList = new ArrayList<>();
        for (JsonAdaptedPatient patient : patients) {
            newPatientList.add(convertToPatient(patient));
        }
        return newPatientList;
    }

    /**
     * Returns true if the imported list contains duplicates.
     */
    public static boolean importsContainDupes(List<Patient> patients) {
        requireNonNull(patients);
        int len = patients.size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (patients.get(i).isSamePatient(patients.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
