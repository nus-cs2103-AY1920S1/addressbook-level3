package seedu.scheduler.logic.commands;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.testutil.SampleInterviewee;
import seedu.scheduler.testutil.SampleInterviewer;

public class ImportCommandTest {
    private static final String INTERVIEWER_FILE_PATH = "src/test/data/ImportsTest/validInterviewerList.csv";
    private static final String INTERVIEWEE_FILE_PATH = "src/test/data/ImportsTest/validIntervieweeList.csv";
    private static final String INTERVIEWER = "interviewer";
    private static final String INTERVIEWEE = "interviewee";

    private Model model = new ModelManager();

    // ===================================== Interviewers ==============================================

    @Test
    public void interviewerImportCommand_success() {
        ImportCommand importCommand = new ImportCommand(new Role(INTERVIEWER),
                new FilePath(INTERVIEWER_FILE_PATH));
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.SUCCESS_MESSAGE, false, false);
        Model expectedModel = new ModelManager();
        for (Interviewer interviewer: SampleInterviewer.getSampleListOfInterviewers()) {
            expectedModel.addInterviewer(interviewer);
        }
        assertCommandSuccess(importCommand, model, expectedCommandResult, expectedModel);
    }


    // ===================================== Interviewees ==============================================

    @Test
    public void intervieweeImportCommand_success() {
        ImportCommand importCommand = new ImportCommand(new Role(INTERVIEWEE),
                new FilePath(INTERVIEWEE_FILE_PATH));
        CommandResult expectedCommandResult = new CommandResult(ImportCommand.SUCCESS_MESSAGE, false, false);
        Model expectedModel = new ModelManager();
        for (Interviewee interviewee: SampleInterviewee.getSampleIntervieweeList()) {
            expectedModel.addInterviewee(interviewee);
        }
        assertCommandSuccess(importCommand, model, expectedCommandResult, expectedModel);
    }

}
