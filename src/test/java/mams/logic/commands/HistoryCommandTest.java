package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccessWithHistory;
import static mams.testutil.TypicalCommandHistory.getTypicalCommandHistory;
import static mams.testutil.TypicalMams.getTypicalMams;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mams.logic.history.CommandHistory;
import mams.logic.history.HistoryFilterSettings;
import mams.logic.history.InputOutput;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

public class HistoryCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory;
    private CommandHistory expectedCommandHistory;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMams(), new UserPrefs());
        expectedModel = new ModelManager(model.getMams(), new UserPrefs());
        commandHistory = getTypicalCommandHistory();
        expectedCommandHistory = new CommandHistory(commandHistory);
    }

    /*
       For integrations tests, we check the interaction of HistoryCommand with an isolated Model
       and CommandHistory.

       We do not check for whether the output of HistoryCommand is added to the CommandHistory.
       Those should be covered in the LogicManagerTest instead.
     */

    /**
     * Integration tests for command execution. This test checks for correctness of feedback, as well as the fact that
     * the HideOutput and showHistory flags in CommandResult are correctly raised (so it can be propagated to GUI).
     */
    @Test
    public void execute_showAll_bothModelAndHistoryFilteredListUnchangedButCorrectCommandResult() {
        // no parameters -> success
        assertCommandSuccessWithHistory(new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE, true, false, false, false),
                expectedModel,
                commandHistory,
                expectedCommandHistory);
    }

    @Test
    public void execute_hideOutput_bothModelAndHistoryFilteredListUnchangedButCorrectCommandResult() {
        // hide command feedback -> success with specific message
        assertCommandSuccessWithHistory(new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.HIDING_COMMAND_OUTPUT_MESSAGE,
                        true, true, false, false),
                expectedModel,
                commandHistory,
                expectedCommandHistory);

    }

    @Test
    public void execute_hideUnsuccessfulCommands_modelUnchangedButHistoryFilteredListChanged() {
        expectedCommandHistory.updateFilteredCommandHistory(InputOutput::checkSuccessful);
        // hide all unsuccessful commands -> success with specific message
        assertCommandSuccessWithHistory(new HistoryCommand(false, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.SHOW_ONLY_SUCCESSFUL_MESSAGE,
                        true, false, false, false),
                expectedModel,
                commandHistory,
                expectedCommandHistory);
    }

    @Test
    public void execute_hideUnsuccessfulCommandsAndOutput_historyFilteredListChangedAndFlagRaisedInCommandResult() {
        expectedCommandHistory.updateFilteredCommandHistory(InputOutput::checkSuccessful);
        // hide command feedback and all unsuccessful commands -> success with specific message
        assertCommandSuccessWithHistory(new HistoryCommand(true, HistoryFilterSettings.SHOW_ONLY_SUCCESSFUL), model,
                new CommandResult(HistoryCommand.SHOWING_HISTORY_MESSAGE + "\n"
                        + HistoryCommand.HIDING_COMMAND_OUTPUT_MESSAGE + "\n"
                        + HistoryCommand.SHOW_ONLY_SUCCESSFUL_MESSAGE,
                        true, true, false, false),
                expectedModel,
                commandHistory,
                expectedCommandHistory);
    }

    @Test
    public void equals() {
        HistoryCommand showOutput = new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL);
        HistoryCommand hideOutput = new HistoryCommand(true, HistoryFilterSettings.SHOW_ALL);
        HistoryCommand anotherShowOutput = new HistoryCommand(false, HistoryFilterSettings.SHOW_ALL);


        // same object -> returns true
        assertTrue(showOutput.equals(showOutput));

        // same values -> returns true
        assertTrue(showOutput.equals(anotherShowOutput));

        // different types -> returns false
        assertFalse(showOutput.equals(1));

        // null -> returns false
        assertFalse(showOutput.equals(null));

        // different internal values -> returns false
        assertFalse(showOutput.equals(hideOutput));
    }
}
