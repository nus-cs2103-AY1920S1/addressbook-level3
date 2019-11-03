package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandSubType;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;
    private String expectedMessage;
    private CommandResult expectedResult;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        model.setContext(Context.newListContactContext());
        expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        expectedModel.setContext(Context.newListContactContext());
    }

    @Test
    public void constructor_calledWithNullArgument_throwsCommandException() {
        assertThrows(NullPointerException.class, () -> new ViewCommand(null, INDEX_FIRST));
        assertThrows(NullPointerException.class, () -> new ViewCommand(CommandSubType.ACTIVITY, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.setContext(Context.newListActivityContext());
        Activity activityToView = model.getFilteredActivityList().get(INDEX_SECOND.getZeroBased());

        Context newActivityContext = new Context(activityToView);
        expectedModel.setContext(newActivityContext);
        expectedMessage = String.format(ViewCommand.MESSAGE_SUCCESS, "activity", activityToView.getTitle());
        expectedResult = new CommandResult(expectedMessage, newActivityContext);

        assertCommandSuccess(new ViewCommand(CommandSubType.ACTIVITY, INDEX_SECOND),
                model, expectedResult, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Filter the list to only show the second person (Bob)
        Person personToView = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        showPersonAtIndex(model, INDEX_SECOND);

        showPersonAtIndex(expectedModel, INDEX_SECOND);
        Context newContactContext = new Context(personToView);
        expectedModel.setContext(newContactContext);
        expectedMessage = String.format(ViewCommand.MESSAGE_SUCCESS, "contact", personToView.getName());
        expectedResult = new CommandResult(expectedMessage, newContactContext);

        assertCommandSuccess(new ViewCommand(CommandSubType.CONTACT, INDEX_FIRST),
                model, expectedResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        model.setContext(Context.newListActivityContext());
        expectedModel.setContext(Context.newListActivityContext());

        Index invalidIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        ViewCommand invalidCommand = new ViewCommand(CommandSubType.ACTIVITY, invalidIndex);

        assertCommandFailure(invalidCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAY_INDEX);
        // Current Context should not change if command failed
        assertEquals(model.getContext(), expectedModel.getContext());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Filter the list to only show the fifth person (Elle)
        showPersonAtIndex(model, INDEX_FIFTH);

        ViewCommand invalidCommand = new ViewCommand(CommandSubType.CONTACT, INDEX_SECOND);

        assertCommandFailure(invalidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAY_INDEX);
        // Current Context should not change if command failed
        assertEquals(model.getContext(), expectedModel.getContext());
    }

    @Test
    public void equals() {
        ViewCommand viewFirstActivity = new ViewCommand(CommandSubType.ACTIVITY, INDEX_FIRST);
        ViewCommand viewFifthPerson = new ViewCommand(CommandSubType.CONTACT, INDEX_FIFTH);

        // identity -> returns true
        assertTrue(viewFirstActivity.equals(viewFirstActivity));

        // same values -> returns true
        assertTrue(viewFifthPerson.equals(new ViewCommand(CommandSubType.CONTACT, INDEX_FIFTH)));

        // different CommandSubType -> returns false
        assertFalse(viewFirstActivity.equals(viewFifthPerson));

        // null -> returns false
        assertFalse(viewFifthPerson.equals(null));

        // same CommandSubType but different index -> returns false
        assertFalse(viewFirstActivity.equals(new ViewCommand(CommandSubType.ACTIVITY, INDEX_SECOND)));
    }
}
