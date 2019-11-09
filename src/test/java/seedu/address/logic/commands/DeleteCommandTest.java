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
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActivityBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBookBuilder;
import seedu.address.testutil.ActivityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
    private Model model2 = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook(getTypicalActivityBook()));
    private Context listContactsContext = Context.newListContactContext();
    private Context listActivitiesContext = Context.newListActivityContext();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETION_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());

        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, listContactsContext);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETION_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, listContactsContext);
    }

    @Test
    public void execute_deletePersonOutsideActivity_success() {
        showPersonAtIndex(model2, INDEX_FIFTH);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model2.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETION_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        expectedModel.deletePerson(personToDelete);
        expectedModel.updateFilteredPersonList(x -> false);

        assertCommandSuccess(deleteCommand, model2, expectedMessage, expectedModel, listContactsContext);
    }

    @Test
    public void execute_deletePersonPreviouslyInActivity_success() {
        showPersonAtIndex(model, INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Activity a = new Activity(new Title("asdf"), personToDelete.getPrimaryKey());
        model.addActivity(a);
        ActivityBook ab = new ActivityBookBuilder().withActivity(a).build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETION_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), new InternalState(), ab);
        expectedModel.getActivityBook().getActivityList().get(0).disinvite(personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.updateFilteredPersonList(x -> false);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, listContactsContext);
    }

    @Test
    public void execute_deletePersonInActivity_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        String errmsg = String.format(DeleteCommand.MESSAGE_PERSON_INVOLVED_ACTIVITY,
                personToDelete.getName());

        Model tempmodel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());

        assertCommandFailure(deleteCommand, tempmodel, errmsg);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_notValidContext_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Model model = new ModelManager();

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_listActivityContext_deleteSuccess() throws CommandException {
        ModelManager expectedModel = new ModelManager(
                model2.getAddressBook(), new UserPrefs(), new InternalState(), model2.getActivityBook());
        model2.setContext(listActivitiesContext);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        expectedModel.setContext(listActivitiesContext);
        Activity activityToDelete = model2.getFilteredActivityList().get(INDEX_FIRST.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETION_SUCCESS, activityToDelete);
        expectedModel.deleteActivity(activityToDelete);

        assertCommandSuccess(deleteCommand, model2, expectedMessage, expectedModel, listActivitiesContext);
    }

    @Test
    public void execute_viewActivityContext_softDeleteExpenseSuccess() throws CommandException {
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);
        Amount amount1 = new Amount(10);
        Amount amount2 = new Amount(20);
        Expense expense1 = new Expense(BENSON.getPrimaryKey(), amount1, "lunch");
        Expense expense2 = new Expense(ALICE.getPrimaryKey(), amount2, "dinner");
        Activity activity = new ActivityBuilder()
                .addPerson(ALICE)
                .addPerson(BENSON)
                .addExpense(expense1)
                .addExpense(expense2)
                .build();

        expectedModel.addActivity(activity);
        expectedModel.setContext(new Context(activity));

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        deleteCommand.execute(expectedModel);

        assertEquals(true, activity.getExpenses().get(INDEX_FIRST.getZeroBased()).isDeleted());
        assertEquals(false, activity.getExpenses().get(INDEX_SECOND.getZeroBased()).isDeleted());

    }

    @Test
    public void execute_viewActivityContextExpenseAlreadyDeleted_throwsCommandException() throws CommandException {
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);
        Amount amount1 = new Amount(10);
        Expense expense1 = new Expense(BENSON.getPrimaryKey(), amount1, "lunch");
        Activity activity = new ActivityBuilder()
                .addPerson(ALICE)
                .addPerson(BENSON)
                .addExpense(expense1)
                .build();

        expectedModel.addActivity(activity);
        expectedModel.setContext(new Context(activity));

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        deleteCommand.execute(expectedModel);

        assertThrows(CommandException.class, () -> deleteCommand.execute(expectedModel));
    }

    @Test
    public void execute_viewActivityContextInvalidIndexExpense_throwsCommandException() throws CommandException {
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);
        Amount amount1 = new Amount(10);
        Expense expense1 = new Expense(BENSON.getPrimaryKey(), amount1, "lunch");
        Activity activity = new ActivityBuilder()
                .addPerson(ALICE)
                .addPerson(BENSON)
                .addExpense(expense1)
                .build();

        expectedModel.addActivity(activity);
        expectedModel.setContext(new Context(activity));

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_SECOND);

        assertThrows(CommandException.class, () -> deleteCommand.execute(expectedModel));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
