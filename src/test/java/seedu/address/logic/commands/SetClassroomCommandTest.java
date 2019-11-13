package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SetClassroomCommand.MESSAGE_CLASSROOM_BLANK;
import static seedu.address.logic.commands.SetClassroomCommand.MESSAGE_CLASSROOM_NOT_FOUND;
import static seedu.address.logic.commands.SetClassroomCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_ONE;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_TWO;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.classroom.Classroom;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SetClassroomCommand}.
 */
public class SetClassroomCommandTest {
    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNotebook(), new UserPrefs());
    @Test
    public void equals() {

        SetClassroomCommand classFirstCommand = new SetClassroomCommand(CLASSROOM_ONE.getClassroomName());
        SetClassroomCommand classSecondCommand = new SetClassroomCommand(CLASSROOM_TWO.getClassroomName());

        // same object -> returns true
        assertTrue(classFirstCommand.equals(classFirstCommand));

        // same values -> returns true
        SetClassroomCommand classFirstCommandCopy = new SetClassroomCommand(CLASSROOM_ONE.getClassroomName());
        assertTrue(classFirstCommand.equals(classFirstCommandCopy));

        // different types -> returns false
        assertFalse(classFirstCommand.equals(1));

        // null -> returns false
        assertFalse(classFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(classFirstCommand.equals(classSecondCommand));
    }
    @Test
    public void execute_classNameNotFound_throwsCommandException() {
        SetClassroomCommand setClassroomCommand = new SetClassroomCommand("Not in model");
        assertCommandFailure(setClassroomCommand, model, MESSAGE_CLASSROOM_NOT_FOUND);
    }
    @Test
    public void execute_blankClassName_throwsCommandException() {
        String expectedMessage = MESSAGE_CLASSROOM_BLANK;
        SetClassroomCommand command = new SetClassroomCommand("");
        assertCommandFailure(command, model, expectedMessage);
    }
    @Test
    public void execute_setValidClassName_success() {
        Classroom classToSet = CLASSROOM_ONE;
        String expectedMessage = String.format(MESSAGE_SUCCESS, classToSet);
        SetClassroomCommand command = new SetClassroomCommand(CLASSROOM_ONE.getClassroomName());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

}
