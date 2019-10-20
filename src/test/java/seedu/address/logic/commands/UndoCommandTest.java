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

class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoEditCommand_success() {
        try {
            new EditCommand(INDEX_FIRST_PERSON, DESC_AMY).execute(model);
            new EditCommand(INDEX_FIRST_PERSON, DESC_BOB).execute(model);
            new EditCommand(INDEX_FIRST_PERSON, DESC_AMY).execute(expectedModel);
            new EditCommand(INDEX_FIRST_PERSON, DESC_BOB).execute(expectedModel);
        } catch (Exception e) {
            fail("Should not throw any exception.");
        }

        // multiple undoable states
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // only one undoable state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new UndoCommand(), model, UndoCommand.UNDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_undoDeleteCommand_success() {
        try {
            new DeleteCommand(INDEX_FIRST_PERSON).execute(model);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(model);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(expectedModel);
            new DeleteCommand(INDEX_FIRST_PERSON).execute(expectedModel);
        } catch (Exception e) {
            fail("Should not throw any exception.");
        }

        // multiple undoable states
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // only one undoable state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new UndoCommand(), model, UndoCommand.UNDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_undoAddCommand_success() {
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

        // multiple undoable states
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // only one undoable state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable state
        assertCommandFailure(new UndoCommand(), model, UndoCommand.UNDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_cannotUndoWithPreviousCommand_failure() {
        new ListPeopleCommand().execute(expectedModel);

        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, expectedModel, UndoCommand.UNDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_cannotUndoWithoutPreviousCommand_failure() {
        Model newModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, newModel, UndoCommand.UNDO_NOT_POSSIBLE);
    }
}
