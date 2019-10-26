package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Attendance;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UndoCommand.
 */
public class UndoCommandTest {
    private Model model;
    private Model expectedModel;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new Attendance(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getAttendance(), new UserPrefs());
    }
    @Test
    public void execute_commandsStackIsEmpty_showsFailureMessage() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
