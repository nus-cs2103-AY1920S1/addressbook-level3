package seedu.address.logic.commands.csvcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.FileUtil;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalParticipants;

public class ExportParticipantCommandTest {

    /**
     * Initializes participant list.
     */
    private void initializeParticipants(Model model) throws AlfredException {
        model.addParticipant(TypicalParticipants.A);
        model.addParticipant(TypicalParticipants.B);
        model.addParticipant(TypicalParticipants.C);
    }

    @Test
    public void equals_sameCommands_returnTrue() {
        String filePath = "src/main/test/Alfred.csv";
        // Same command returns true
        ExportParticipantCommand command1 = new ExportParticipantCommand(filePath);
        assertEquals(command1, command1);

        // Same parameter returns true
        ExportParticipantCommand command2 = new ExportParticipantCommand(filePath);
        assertEquals(command1, command2);

        // Empty strings result in default names
        command1 = new ExportParticipantCommand("");
        command2 = new ExportParticipantCommand(ExportParticipantCommand.DEFAULT_FILE_PATH.toString());
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentCommands_returnFalse() {
        String filePath = "src/main/test/Alfred.csv";
        // Different class returns false
        ExportParticipantCommand command1 = new ExportParticipantCommand(filePath);
        ExportTeamCommand exportTeamCommand = new ExportTeamCommand(filePath);
        assertNotEquals(command1, exportTeamCommand);

        // Different parameters returns false
        ExportParticipantCommand command2 = new ExportParticipantCommand("src/main/test/Alfred1.csv");
        assertNotEquals(command1, command2);
    }

    @Test
    public void constructor_nonCsvFilePassed_assertionErrorThrown() {
        String filePath = "Alfred.txt";
        assertThrows(AssertionError.class, () -> new ExportParticipantCommand(filePath));
    }

    @Test
    public void execute_emptyModelPassed_successWithNoFileCreated() throws AlfredException {
        Model emptyModel = new ModelManagerStub();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInCsvUtilTestFolder(fileName).toString();
        assertEquals(
                ExportParticipantCommand.MESSAGE_EMPTY_DATA,
                new ExportParticipantCommand(filePath).execute(emptyModel).getFeedbackToUser()
        );
        assertFalse(TestUtil.getFilePathInCsvUtilTestFolder(fileName).toFile().exists());
    }

    @Test
    public void execute_validParametersPassed_success() throws AlfredException, IOException {
        Model model = new ModelManagerStub();
        initializeParticipants(model);

        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedParticipants.csv").toFile();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInSandboxFolder(fileName).toString();
        String expectedMessage = String.format(ExportParticipantCommand.MESSAGE_SUCCESS, filePath);

        assertEquals(expectedMessage, new ExportParticipantCommand(filePath).execute(model).getFeedbackToUser());

        File outcomeFile = TestUtil.getFilePathInSandboxFolder(fileName).toFile();
        outcomeFile.deleteOnExit();
        assertTrue(FileUtil.hasEqualContents(expectedFile, outcomeFile));
    }

}
