package cs.f10.t1.nursetraverse.importexport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.core.JsonProcessingException;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.commons.util.FileUtil;
import cs.f10.t1.nursetraverse.importexport.exceptions.ExportingException;
import cs.f10.t1.nursetraverse.importexport.exceptions.ImportingException;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.TypicalCsv;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class CsvUtilTest {

    private static final String testFileName = "test.csv";
    private static final String sampleFileName = "sample.csv";

    private static final String testComparisonCsv = TypicalCsv.TYPICAL_CSV_STRING;

    private static final String testInvalidCsv = TypicalCsv.INVALID_CSV_STRING;

    private static final List<Patient> samplePatients = TypicalPatients.getTypicalPatients();
    private static final List<Patient> samplePatientsWithoutVisit =
            TypicalPatients.getTypicalPatientsWithoutVisit();

    @TempDir
    public Path tempFolder;

    // Writing/Export function tests ==============================================================

    @Test
    public void writePatientsToCsv_nullArgs_throwsException() {

        // null patients, valid filename
        Assert.assertThrows(NullPointerException.class, () ->
                CsvUtil.writePatientsToCsv(null, sampleFileName));

        // valid patients, null filename
        Assert.assertThrows(NullPointerException.class, () ->
                CsvUtil.writePatientsToCsv(samplePatients, null));

        // null patients, null filename
        Assert.assertThrows(NullPointerException.class, () ->
                CsvUtil.writePatientsToCsv(null, null));
    }

    @Test
    public void writePatientsToCsv_validInput_correctFileWritten() throws ExportingException, IOException {
        // ./tmp/junit.../test.csv
        String tempTestPathString = tempFolder.toString() + "/" + testFileName;
        // ./tmp/junit.../sample.csv
        String tempSamplePathString = tempFolder.toString() + "/" + sampleFileName;
        Path tempTestPath = Paths.get(tempTestPathString);
        Path tempSamplePath = Paths.get(tempSamplePathString);

        // Write sample patients with CsvUtil to test.csv
        CsvUtil.writePatientsToCsv(samplePatients, tempTestPathString);
        // Write correct output with FileUtil to test.csv
        FileUtil.writeToFile(tempSamplePath, testComparisonCsv);
        // Check that CsvUtil's output matches the intended output
        assertEquals(FileUtil.readFromFile(tempTestPath),
                FileUtil.readFromFile(tempSamplePath));
    }

    @Test
    public void writePatientsToCsv_ifFileExists_throwsException() throws IOException {
        // ./tmp/junit.../test.csv
        String tempTestPathString = tempFolder.toString() + "/" + testFileName;
        Path tempTestPath = Paths.get(tempTestPathString);

        // Write some correct output with FileUtil to test.csv
        FileUtil.writeToFile(tempTestPath, testComparisonCsv);
        // Try writing again with CsvUtil to the same file, Exception should be thrown.
        Assert.assertThrows(ExportingException.class, () ->
                CsvUtil.writePatientsToCsv(samplePatients, tempTestPathString));
    }

    @Test
    public void getCsvStringFromPatients_validInput_success() throws JsonProcessingException {
        String testCsvString = CsvUtil.getCsvStringFromPatients(samplePatients);
        assertEquals(testCsvString, testComparisonCsv);
    }

    // Reading/Import function tests ==============================================================

    @Test
    public void readPatientsFromCsv_nullArgs_throwsException() {
        Assert.assertThrows(NullPointerException.class, () ->
                CsvUtil.readPatientsFromCsv(null));
    }

    @Test
    public void readPatientsFromCsv_missingFile_throwsException() {
        String nonExistentPathString = "/not/a/valid/path.csv";
        Assert.assertThrows(ImportingException.class, () ->
                CsvUtil.readPatientsFromCsv(nonExistentPathString));
    }

    @Test
    public void readPatientsFromCsv_invalidFile_throwsException() throws IOException {
        // ./tmp/junit.../test.csv
        String tempTestPathString = tempFolder.toString() + "/" + testFileName;
        Path tempTestPath = Paths.get(tempTestPathString);

        // Write an invalid file to test.csv
        FileUtil.writeToFile(tempTestPath, testInvalidCsv);

        // Try and read the invalid file
        Assert.assertThrows(IllegalValueException.class, () ->
                CsvUtil.readPatientsFromCsv(tempTestPathString));
    }

    @Test
    public void readPatientsFromCsv_validInput_success() throws IOException, IllegalValueException, ImportingException {
        // ./tmp/junit.../test.csv
        String tempTestPathString = tempFolder.toString() + "/" + testFileName;
        Path tempTestPath = Paths.get(tempTestPathString);

        // Write an valid file to test.csv
        FileUtil.writeToFile(tempTestPath, testComparisonCsv);

        List<Patient> readPatientList = CsvUtil.readPatientsFromCsv(tempTestPathString);
        assertEquals(readPatientList, samplePatientsWithoutVisit);
    }

    @Test
    public void importContainDupes_varArgs() {
        List<Patient> samplePatientsWithDupes = TypicalPatients.getTypicalPatients();
        samplePatientsWithDupes.addAll(samplePatientsWithDupes);

        assertTrue(() -> CsvUtil.importsContainDupes(samplePatientsWithDupes));

        assertFalse(() -> CsvUtil.importsContainDupes(samplePatients));

        Assert.assertThrows(NullPointerException.class, () ->
                CsvUtil.importsContainDupes(null));
    }
}
