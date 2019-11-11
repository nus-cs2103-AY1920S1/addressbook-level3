package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Notebook;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;


public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());

    @Test
    public void execute_noNewAction_failure() {
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_UNDO_FAILURE);
    }

    @Test
    public void execute_newActionDoesNotChangeState_failure() {
        model.displayAssignments();
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_UNDO_FAILURE);
    }

    @Test
    public void execute_newActionChangeState_success() {
        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());

        Assignment toAdd = new Assignment(new AssignmentName("Social Studies Worksheet 1"),
                new AssignmentDeadline("31/12/2019 2020"));
        model.addAssignment(toAdd);
        model.saveState();
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = String.format(UndoCommand.MESSAGE_UNDO_SUCCESS);


        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);

    }

}
