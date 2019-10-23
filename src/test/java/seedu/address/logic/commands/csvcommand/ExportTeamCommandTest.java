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
import seedu.address.testutil.TypicalTeams;

public class ExportTeamCommandTest {

    /**
     * Initializes team list.
     */
    private void initializeTeams(Model model) throws AlfredException {
        model.addTeam(TypicalTeams.A);
        model.addTeam(TypicalTeams.B);
        model.addTeam(TypicalTeams.C);
    }

    @Test
    public void equals_sameCommands_returnTrue() {
        String filePath = "src/main/test/Alfred.csv";
        // Same command returns true
        ExportTeamCommand command1 = new ExportTeamCommand(filePath);
        assertEquals(command1, command1);

        // Same parameter returns true
        ExportTeamCommand command2 = new ExportTeamCommand(filePath);
        assertEquals(command1, command2);

        // Empty strings result in default names
        command1 = new ExportTeamCommand("");
        command2 = new ExportTeamCommand(ExportTeamCommand.DEFAULT_FILE_PATH.toString());
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentCommands_returnFalse() {
        String filePath = "src/main/test/Alfred.csv";
        // Different class returns false
        ExportTeamCommand command1 = new ExportTeamCommand(filePath);
        ExportMentorCommand exportMentorCommand = new ExportMentorCommand(filePath);
        assertNotEquals(command1, exportMentorCommand);

        // Different parameters returns false
        ExportTeamCommand command2 = new ExportTeamCommand("src/main/test/Alfred1.csv");
        assertNotEquals(command1, command2);
    }

    @Test
    public void constructor_nonCsvFilePassed_assertionErrorThrown() {
        String filePath = "Alfred.txt";
        assertThrows(AssertionError.class, () -> new ExportTeamCommand(filePath));
    }

    @Test
    public void execute_emptyModelPassed_successWithNoFileCreated() throws AlfredException {
        Model emptyModel = new ModelManagerStub();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInCsvUtilTestFolder(fileName).toString();
        assertEquals(
                ExportTeamCommand.MESSAGE_EMPTY_DATA,
                new ExportTeamCommand(filePath).execute(emptyModel).getFeedbackToUser()
        );
        assertFalse(TestUtil.getFilePathInCsvUtilTestFolder(fileName).toFile().exists());
    }

    @Test
    public void execute_validParametersPassed_success() throws AlfredException, IOException {
        Model model = new ModelManagerStub();
        initializeTeams(model);

        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedTeams.csv").toFile();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInSandboxFolder(fileName).toString();
        String expectedMessage = String.format(ExportTeamCommand.MESSAGE_SUCCESS, filePath);

        assertEquals(expectedMessage, new ExportTeamCommand(filePath).execute(model).getFeedbackToUser());

        File outcomeFile = TestUtil.getFilePathInSandboxFolder(fileName).toFile();
        outcomeFile.deleteOnExit();
        assertTrue(FileUtil.hasEqualContents(expectedFile, outcomeFile));
    }

}
