package seedu.address.logic.commands;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class ImportCommandTest {
    private static final String INTERVIEWER_FILE_PATH = "src/test/data/ImportsTest/InterviewerTestData.csv";
    private static final String INTERVIEWEE_FILE_PATH = "src/test/data/ImportsTest/IntervieweeTestData.csv";
    private static final String INTERVIEWER = "interviewer";
    private static final String INTERVIEWEE = "interviewee";
    private Model model = new ModelManager();

    @Test
    public void interviewerImportCommandSuccess() {
        ImportCommand importCommand = new ImportCommand(INTERVIEWER + " " + INTERVIEWER_FILE_PATH);
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.SUCCESS_MESSAGE, false, false);
        Model expectedModel = model;
        assertCommandSuccess(importCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void intervieweeImportCommandSuccess() {
        ImportCommand importCommand = new ImportCommand(INTERVIEWEE + " " + INTERVIEWEE_FILE_PATH);
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.SUCCESS_MESSAGE, false, false);
        Model expectedModel = model;
        assertCommandSuccess(importCommand, model, expectedCommandResult, expectedModel);
    }

}
