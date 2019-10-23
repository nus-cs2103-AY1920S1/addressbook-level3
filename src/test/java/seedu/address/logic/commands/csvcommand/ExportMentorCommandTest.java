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
import seedu.address.testutil.TypicalMentors;

public class ExportMentorCommandTest {

    /**
     * Initializes mentor list.
     */
    private void initializeMentors(Model model) throws AlfredException {
        model.addMentor(TypicalMentors.A);
        model.addMentor(TypicalMentors.B);
        model.addMentor(TypicalMentors.C);
    }

    @Test
    public void equals_sameCommands_returnTrue() {
        String filePath = "src/main/test/Alfred.csv";
        // Same command returns true
        ExportMentorCommand command1 = new ExportMentorCommand(filePath);
        assertEquals(command1, command1);

        // Same parameter returns true
        ExportMentorCommand command2 = new ExportMentorCommand(filePath);
        assertEquals(command1, command2);

        // Empty strings result in default names
        command1 = new ExportMentorCommand("");
        command2 = new ExportMentorCommand(ExportMentorCommand.DEFAULT_FILE_PATH.toString());
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentCommands_returnFalse() {
        // Different class returns false
        String filePath = "src/main/test/Alfred.csv";
        ExportMentorCommand command1 = new ExportMentorCommand(filePath);
        ExportParticipantCommand exportParticipantCommand = new ExportParticipantCommand(filePath);
        assertNotEquals(command1, exportParticipantCommand);

        // Different parameters returns false
        ExportMentorCommand command2 = new ExportMentorCommand("src/main/test/Alfred1.csv");
        assertNotEquals(command1, command2);
    }

    @Test
    public void constructor_nonCsvFilePassed_assertionErrorThrown() {
        String filePath = "Alfred.txt";
        assertThrows(AssertionError.class, () -> new ExportMentorCommand(filePath));
    }

    @Test
    public void execute_emptyModelPassed_successWithNoFileCreated() throws AlfredException {
        Model emptyModel = new ModelManagerStub();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInCsvUtilTestFolder(fileName).toString();
        assertEquals(
                ExportMentorCommand.MESSAGE_EMPTY_DATA,
                new ExportMentorCommand(filePath).execute(emptyModel).getFeedbackToUser()
        );
        assertFalse(TestUtil.getFilePathInCsvUtilTestFolder(fileName).toFile().exists());
    }

    @Test
    public void execute_validParametersPassed_success() throws AlfredException, IOException {
        Model model = new ModelManagerStub();
        initializeMentors(model);

        File expectedFile = TestUtil.getFilePathInCsvUtilTestFolder("ExpectedMentors.csv").toFile();
        String fileName = "Alfred.csv";
        String filePath = TestUtil.getFilePathInSandboxFolder(fileName).toString();
        String expectedMessage = String.format(ExportMentorCommand.MESSAGE_SUCCESS, filePath);

        assertEquals(expectedMessage, new ExportMentorCommand(filePath).execute(model).getFeedbackToUser());

        File outcomeFile = TestUtil.getFilePathInSandboxFolder(fileName).toFile();
        outcomeFile.deleteOnExit();
        assertTrue(FileUtil.hasEqualContents(expectedFile, outcomeFile));
    }

}
