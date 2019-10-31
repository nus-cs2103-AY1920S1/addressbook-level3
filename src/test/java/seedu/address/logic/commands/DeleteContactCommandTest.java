//package seedu.planner.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.planner.logic.commands.CommandTestUtil.showContactAtIndex;
//import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
//import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
//import static seedu.planner.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
//import static seedu.planner.testutil.activity.TypicalActivity.getTypicalActivityManager;
//import static seedu.planner.testutil.contact.TypicalContacts.getTypicalContactManager;
//import static seedu.planner.testutil.day.TypicalDays.getTypicalItinerary;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.planner.commons.core.Messages;
//import seedu.planner.commons.core.index.Index;
//import seedu.planner.model.Model;
//import seedu.planner.model.ModelManager;
//import seedu.planner.model.UserPrefs;
//import seedu.planner.model.contact.Contact;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class DeleteContactCommandTest {
//
//    private Model model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
//            getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);
//
//        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
//                model.getContacts(), model.getItinerary(), new UserPrefs());
//        expectedModel.deleteContact(contactToDelete);
//
//        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
//        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showContactAtIndex(model, INDEX_FIRST_CONTACT);
//
//        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
//        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);
//
//        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);
//
//        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
//                model.getItinerary(), new UserPrefs());
//
//        expectedModel.deleteContact(contactToDelete);
//        showNoContact(expectedModel);
//
//        assertCommandSuccess(deleteContactCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showContactAtIndex(model, INDEX_FIRST_CONTACT);
//
//        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getContacts().getContactList().size());
//
//        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void equals() {
//        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);
//        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(INDEX_SECOND_CONTACT);
//
//        // same object -> returns true
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
//
//        // same values -> returns true
//        DeleteContactCommand deleteFirstContactCommandCopy = new DeleteContactCommand(INDEX_FIRST_CONTACT);
//        assertTrue(deleteFirstCommand.equals(deleteFirstContactCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteFirstCommand.equals(null));
//
//        // different contacts -> returns false
//        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show no one.
//     */
//    private void showNoContact(Model model) {
//        model.updateFilteredContactList(p -> false);
//
//        assertTrue(model.getFilteredContactList().isEmpty());
//    }
//}
//
