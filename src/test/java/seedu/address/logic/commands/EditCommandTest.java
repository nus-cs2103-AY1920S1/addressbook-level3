package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.address.testutil.TypicalEateries.getTypicalOpenAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.eatery.Eatery;
import seedu.address.testutil.EateryBuilder;
import seedu.address.testutil.EditEateryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalOpenAddressBook(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Eatery editedEatery = new EateryBuilder().build();
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder(editedEatery).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EATERY_SUCCESS, editedEatery);

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), editedEatery);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY, new EditEateryDescriptor());
        Eatery editedEatery = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EATERY_SUCCESS, editedEatery);

        Model expectedModel =
                new ModelManager(new AddressBook(model.getAddressBook()), model.getFeedList(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEateryUnfilteredList_failure() {
        Eatery firstEatery = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder(firstEatery).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_EATERY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EATERY);
    }

    @Test
    public void execute_duplicateEateryFilteredList_failure() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);

        // edit eatery in filtered list into a duplicate in address book
        Eatery eateryInList = model.getAddressBook().getEateryList().get(INDEX_SECOND_EATERY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY,
                new EditEateryDescriptorBuilder(eateryInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EATERY);
    }

    @Test
    public void execute_invalidEateryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEateryIndexFilteredList_failure() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);
        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEateryList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditEateryDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_EATERY, DESC_AMY);

        // same values -> returns true
        EditEateryDescriptor copyDescriptor = new EditEateryDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_EATERY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_EATERY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_EATERY, DESC_BOB)));
    }

}
