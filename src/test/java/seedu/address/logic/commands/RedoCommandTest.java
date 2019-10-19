package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

class RedoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel;

    /**
     * Performs an undo for expectedModel and model.
     */
    private void performUndo() {
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();
        model.undoAddressBook();
        model.undoAddressBook();
    }

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_redoEditCommand_success() {
        try {
            new EditCommand(INDEX_FIRST_PERSON, DESC_AMY).execute(model);
            new EditCommand(INDEX_FIRST_PERSON, DESC_BOB).execute(model);
            new EditCommand(INDEX_FIRST_PERSON, DESC_AMY).execute(expectedModel);
            new EditCommand(INDEX_FIRST_PERSON, DESC_BOB).execute(expectedModel);
        } catch (Exception e) {
            fail("Should not throw any exception.");
        }

        performUndo();

        // multiple redoable states
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new RedoCommand(), model, RedoCommand.REDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_redoDeleteCommand_success() {
        try {
            new DeleteCommand(INDEX_FIRST_PERSON).execute(model);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(model);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(expectedModel);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(expectedModel);
        } catch (Exception e) {
            fail("Should not throw any exception.");
        }

        performUndo();

        // multiple redoable states
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new RedoCommand(), model, RedoCommand.REDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_redoAddCommand_success() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName(VALID_NAME_BOB).build();

        try {
            new AddCommand(person1).execute(model);
            new AddCommand(person2).execute(model);
            new AddCommand(person1).execute(expectedModel);
            new AddCommand(person2).execute(expectedModel);
        } catch (Exception e) {
            fail("Should not throw any exception.");
        }

        performUndo();

        // multiple redoable states
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new RedoCommand(), model, RedoCommand.REDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_cannotRedoWithPreviousCommand_failure() {
        new ListPeopleCommand().execute(expectedModel);
        assertCommandFailure(new RedoCommand(), expectedModel, RedoCommand.REDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_cannoRedoWithoutPreviousCommand_failure() {
        Model newModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(new RedoCommand(), newModel, RedoCommand.REDO_NOT_POSSIBLE);
    }
}
