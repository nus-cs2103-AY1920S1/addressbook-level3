package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandSubType;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
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
        expectedResult = new CommandResult(expectedMessage, ContextType.LIST_CONTACT);
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
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(CommandSubType.CONTACT),
                model, expectedResult, expectedModel);
    }

    @Test
    public void execute_activityListIsFiltered_showsEverything() {
        expectedModel.setContext(Context.newListActivityContext());
        expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "activities");
        expectedResult = new CommandResult(expectedMessage, ContextType.LIST_ACTIVITY);

        model.updateFilteredActivityList((activity) ->
            activity.getTitle().equals(new Title("Lunch")));

        assertCommandSuccess(new ListCommand(CommandSubType.ACTIVITY),
                model, expectedResult, expectedModel);
    }
}
