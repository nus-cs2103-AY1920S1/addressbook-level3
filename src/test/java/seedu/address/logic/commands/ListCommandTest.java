package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandSubType;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Title;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private String expectedMessage;
    private CommandResult expectedResult;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        expectedModel.setContext(Context.newListContactContext());
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "contacts");
        expectedResult = new CommandResult(expectedMessage, Context.newListContactContext());
    }

    @Test
    public void constructor_nullListCommandSubType_throwsCommandException() {
        assertThrows(NullPointerException.class, () -> new ListCommand(null));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(CommandSubType.CONTACT),
                model, expectedResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(CommandSubType.CONTACT),
                model, expectedResult, expectedModel);
    }

    @Test
    public void execute_activityListIsFiltered_showsEverything() {
        expectedModel.setContext(Context.newListActivityContext());
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "activities");
        expectedResult = new CommandResult(expectedMessage, Context.newListActivityContext());

        model.updateFilteredActivityList((activity) ->
            activity.getTitle().equals(new Title("Lunch")));

        assertCommandSuccess(new ListCommand(CommandSubType.ACTIVITY),
                model, expectedResult, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listPersons = new ListCommand(CommandSubType.CONTACT);
        ListCommand listActivities = new ListCommand(CommandSubType.ACTIVITY);

        // identity -> returns true
        assertTrue(listPersons.equals(listPersons));

        // same values -> returns true
        assertTrue(listActivities.equals(new ListCommand(CommandSubType.ACTIVITY)));

        // different CommandSubType -> returns false
        assertFalse(listPersons.equals(listActivities));

        // null -> returns false
        assertFalse(listActivities.equals(null));
    }
}
