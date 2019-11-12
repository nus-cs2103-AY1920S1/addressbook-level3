package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.executeCommandAndUpdateStateManager;
import static io.xpire.logic.commands.UndoCommand.MESSAGE_UNDO_FAILURE;
import static io.xpire.model.ListType.XPIRE;
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
 * Tests commands that are not undoable.
 */
public class UndoCommandTest {

    private Model model;
    private StateManager stateManager;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        stateManager = new StackManager();
    }

    //--------------------NON-UNDOABLE COMMANDS--------------------------------------------------------------------

    //Testing Undo for TagCommand(Show)(Should not Undo)
    @Test
    public void execute_undoTagShow_throwsCommandException() throws CommandException, ParseException {
        TagCommand tagCommand = new TagCommand(XPIRE);
        UndoCommand undoCommand = new UndoCommand();
        executeCommandAndUpdateStateManager(model, tagCommand, stateManager);
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

    //Testing Undo for ExitCommand(Should not Undo)
    @Test
    public void execute_undoExit_throwsCommandException() throws CommandException, ParseException {
        ExitCommand exitCommand = new ExitCommand();
        UndoCommand undoCommand = new UndoCommand();
        executeCommandAndUpdateStateManager(model, exitCommand, stateManager);
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

    //Testing Undo for HelpCommand(Should not Undo)
    @Test
    public void execute_undoHelp_throwsCommandException() throws CommandException, ParseException {
        HelpCommand helpCommand = new HelpCommand();
        UndoCommand undoCommand = new UndoCommand();
        executeCommandAndUpdateStateManager(model, helpCommand, stateManager);
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

    //Testing Undo for ExportCommand(Should not Undo)
    @Test
    public void execute_undoExport_throwsCommandException() throws CommandException, ParseException {
        ExportCommand exportCommand = new ExportCommand();
        UndoCommand undoCommand = new UndoCommand();
        executeCommandAndUpdateStateManager(model, exportCommand, stateManager);
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

    //Testing Undo if there are no Undoable States
    @Test
    public void execute_noUndoableStates_throwsCommandException() throws CommandException, ParseException {
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

}
