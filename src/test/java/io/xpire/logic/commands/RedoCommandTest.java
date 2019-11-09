package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.RedoCommand.MESSAGE_REDO_FAILURE;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.state.StackManager;
import io.xpire.model.state.StateManager;

/**
 * Test for Redo Command. Since commands that are not undoable are also not redoable, we only test if there
 * are no more Redoable Commands
 */
public class RedoCommandTest {

    private Model model;
    private StateManager stateManager;

    @BeforeEach
    public void setUp() throws CommandException, ParseException {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        stateManager = new StackManager();

    }

    //Testing Redo if there are no Redoable States
    @Test
    public void execute_noRedoableStates_throwsCommandException() throws CommandException, ParseException {
        RedoCommand redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, MESSAGE_REDO_FAILURE);
    }
}
