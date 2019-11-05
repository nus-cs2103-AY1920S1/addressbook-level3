package cs.f10.t1.nursetraverse.logic.commands;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.util.FileUtil;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.testutil.TypicalCsv;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class ImportMergeCommandTest {

    private static final String testFileName = "testFileName";
    private static final String emptyFileName = "emptyFileName";
    private static final String nonExistentFileName = "nonExistentFileNameAvoidClash";
    private static File tempFile;
    private static File emptyFile;

    private Model model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs());

    @BeforeAll
    public static void setUp() throws IOException {
        tempFile = File.createTempFile(testFileName, ".csv", new File("imports"));
        FileUtil.writeToFile(tempFile.toPath(), TypicalCsv.TYPICAL_CSV_STRING);
        tempFile.deleteOnExit();

        emptyFile = File.createTempFile(emptyFileName, ".csv", new File("imports"));
        emptyFile.deleteOnExit();
    }

    @Test
    public void execute_nonExistentFile_failure() {
        CommandTestUtil.assertCommandFailure(
                new ImportMergeCommand(nonExistentFileName), model,
                String.format(ImportMergeCommand.MESSAGE_FILE_DOES_NOT_EXIST, nonExistentFileName));
    }

    @Test
    public void execute_emptyFile_failure() {
        CommandTestUtil.assertCommandFailure(
                new ImportMergeCommand(
                        emptyFile.getName().replace(".csv", "")),
                model, ImportMergeCommand.MESSAGE_FILE_EMPTY
        );
    }

    @Test
    public void execute_duplicatePatients_failure() {
        CommandTestUtil.assertCommandFailure(
                new ImportMergeCommand(
                        tempFile.getName().replace(".csv", "")),
                model, ImportMergeCommand.MESSAGE_DUPLICATE_CSV_PATIENTS
        );
    }

    @Test
    public void execute_validCsv_success() {
        Model testModel = new ModelManager(new PatientBook(), new UserPrefs());

        PatientBook expectedPatientBook = new PatientBook();
        expectedPatientBook.setPatients(TypicalPatients.getTypicalPatientsWithoutVisit());
        Model expectedModel = new ModelManager(expectedPatientBook, new UserPrefs());

        CommandTestUtil.assertCommandSuccess(
                new ImportMergeCommand(
                        tempFile.getName().replace(".csv", "")),
                testModel, ImportMergeCommand.MESSAGE_SUCCESS, expectedModel
        );
    }
}
