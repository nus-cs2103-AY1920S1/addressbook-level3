package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.Messages;

import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;

/**
 * Contains integration tests (interaction with Model)
 */
public class DisplayCommandTest {
    private static final String INTERVIEWEE = "interviewee";
    private static final String INTERVIEWER = "interviewer";
    private static final String SCHEDULE = "schedule";

    private Model model = new ModelManager();

    // ===================================== Interviewee ==============================================
    @Test
    public void displayIntervieweeTab_success() {
        DisplayCommand displayCommand = new DisplayCommand(INTERVIEWEE);
        CommandResult expectedCommandResult = new CommandResult(
                INTERVIEWEE + DisplayCommand.CHANGE_TAB_SUCCESS, false, false);
        Model expectedModel = new ModelManager();
        assertCommandSuccess(displayCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_displayIntervieweeCommandInvalid_throwsCommandException() {
        DisplayCommand displayCommand = new DisplayCommand("invalidCommand");
        Model expectedModel = new ModelManager();
        assertCommandFailure(displayCommand, expectedModel, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DisplayCommand.MESSAGE_USAGE));
    }

    // ===================================== Interviewer ==============================================
    @Test
    public void displayInterviewerTab_success() {
        DisplayCommand displayCommand = new DisplayCommand(INTERVIEWER);
        CommandResult expectedCommandResult = new CommandResult(
                INTERVIEWER + DisplayCommand.CHANGE_TAB_SUCCESS, false, false);
        Model expectedModel = new ModelManager();
        assertCommandSuccess(displayCommand, model, expectedCommandResult, expectedModel);
    }

    // ===================================== Schedule ==============================================
    @Test
    public void displayScheduleTab_success() {
        DisplayCommand displayCommand = new DisplayCommand(SCHEDULE);
        CommandResult expectedCommandResult = new CommandResult(
                SCHEDULE + DisplayCommand.CHANGE_TAB_SUCCESS, false, false);
        Model expectedModel = new ModelManager();
        assertCommandSuccess(displayCommand, model, expectedCommandResult, expectedModel);
    }
}

