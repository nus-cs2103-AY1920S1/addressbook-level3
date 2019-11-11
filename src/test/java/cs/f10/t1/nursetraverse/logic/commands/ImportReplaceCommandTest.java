package cs.f10.t1.nursetraverse.logic.commands;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.util.FileUtil;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalCsv;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class ImportReplaceCommandTest {
    private static final String testFileName = "testFileName";
    private static final String emptyFileName = "emptyFileName";
    private static final String nonExistentFileName = "nonExistentFileNameAvoidClash";
    private static File tempFile;
    private static File emptyFile;

    private Model model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                           TypicalAppointments.getTypicalAppointmentBook());

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
                new ImportReplaceCommand(nonExistentFileName), model,
                String.format(ImportReplaceCommand.MESSAGE_FILE_DOES_NOT_EXIST, nonExistentFileName));
    }

    @Test
    public void execute_emptyFile_failure() {
        CommandTestUtil.assertCommandFailure(
                new ImportReplaceCommand(
                        emptyFile.getName().replace(".csv", "")),
                model, ImportReplaceCommand.MESSAGE_FILE_EMPTY
        );
    }

    @Test
    public void execute_validCsv_success() {
        Model testModel = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                           TypicalAppointments.getTypicalAppointmentBook());

        PatientBook expectedPatientBook = new PatientBook();
        AppointmentBook expectedAppointmentBook = new AppointmentBook();
        expectedPatientBook.setPatients(TypicalPatients.getTypicalPatientsWithoutVisit());
        expectedAppointmentBook.setAppointments(TypicalAppointments.getTypicalAppointments());
        Model expectedModel = new ModelManager(expectedPatientBook, new UserPrefs(), expectedAppointmentBook);

        CommandTestUtil.assertCommandSuccess(
                new ImportReplaceCommand(
                        tempFile.getName().replace(".csv", "")),
                testModel, ImportReplaceCommand.MESSAGE_SUCCESS, expectedModel
        );
    }
}
