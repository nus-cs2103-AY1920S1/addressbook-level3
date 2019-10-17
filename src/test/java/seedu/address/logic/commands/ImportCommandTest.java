package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class ImportCommandTest {
    private static final String SUCCESS_FILE_PATH = "src/test/data/ImportsTest/test.csv";
    private static final String INVALID_FILE_PATH = "invalid/file/path";
    private static final String INTERVIEWER = "interviewer";
    private Model model = new ModelManager();

    @Test
    public void interviewerImportCommandSuccess() {
        ImportCommand importCommand = new ImportCommand(INTERVIEWER + " " + SUCCESS_FILE_PATH);
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.SUCCESS_MESSAGE, false, false);
        Model expectedModel = model;
        assertCommandSuccess(importCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void interviewerImportCommandFailure() {
        ImportCommand importCommand = new ImportCommand(INTERVIEWER + " " + INVALID_FILE_PATH);
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.FILE_DOES_NOT_EXIST, false, false);
        assertEquals(importCommand.execute(model), expectedCommandResult);
    }

}
