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


public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());

    @Test
    public void execute_noNewAction_failure() {
        RedoCommand redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_REDO_FAILURE);
    }

    @Test
    public void execute_newActionDoesNotChangeState_failure() {
        model.displayAssignments();
        RedoCommand redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_REDO_FAILURE);
    }

    @Test
    public void execute_newActionChangeState_success() {

        Assignment toAdd = new Assignment(new AssignmentName("Social Studies Worksheet 1"),
                new AssignmentDeadline("31/12/2019 2020"));
        model.addAssignment(toAdd);
        model.saveState();
        model.undo();
        RedoCommand redoCommand = new RedoCommand();

        Model expectedModel = new ModelManager(new Notebook(model.getNotebook()), new UserPrefs());
        String expectedMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS);

        assertCommandSuccess(redoCommand, model, expectedMessage, expectedModel);

    }

}
