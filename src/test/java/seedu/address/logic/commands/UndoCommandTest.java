package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SMOKER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoEditCommand() {
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
    public void execute_undoDeleteCommand() {
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
    public void execute_undoAddCommand() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withNric(VALID_NRIC_BOB).build();

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
    public void execute_undoAddTagCommand() {
        try {
            new AddTagCommand(INDEX_FIRST_PERSON, VALID_TAG_DIABETIC).execute(model);
            new AddTagCommand(INDEX_FIRST_PERSON, VALID_TAG_SMOKER).execute(model);
            new AddTagCommand(INDEX_FIRST_PERSON, VALID_TAG_DIABETIC).execute(expectedModel);
            new AddTagCommand(INDEX_FIRST_PERSON, VALID_TAG_SMOKER).execute(expectedModel);
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
    public void execute_undoAssignUnassignPolicyCommand() {
        Policy policy = model.getAddressBook().getPolicyList().get(INDEX_SECOND_POLICY.getZeroBased());

        try {
            new AssignPolicyCommand(INDEX_FIRST_PERSON, policy.getName()).execute(model);
            new UnassignPolicyCommand(INDEX_FIRST_PERSON, policy.getName()).execute(model);
            new AssignPolicyCommand(INDEX_FIRST_PERSON, policy.getName()).execute(expectedModel);
            new UnassignPolicyCommand(INDEX_FIRST_PERSON, policy.getName()).execute(expectedModel);
        } catch (Exception e) {
            fail(e.getMessage());
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
    public void execute_noDataChangePerformed_failure() {
        new ListPeopleCommand().execute(expectedModel);
        new ListPolicyCommand().execute(expectedModel);

        assertCommandFailure(new UndoCommand(), expectedModel, UndoCommand.UNDO_NOT_POSSIBLE);
    }

    @Test
    public void execute_nothingPerformed_failure() {
        assertCommandFailure(new UndoCommand(), expectedModel, UndoCommand.UNDO_NOT_POSSIBLE);
    }
}
