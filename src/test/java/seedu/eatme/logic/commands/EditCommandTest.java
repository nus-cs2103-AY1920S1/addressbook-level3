package seedu.eatme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.DESC_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.DESC_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eatme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eatme.logic.commands.CommandTestUtil.showEateryAtIndex;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.testutil.EateryBuilder;
import seedu.eatme.testutil.EditEateryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalOpenEateryList(), getTypicalFeedList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Eatery editedEatery = new EateryBuilder().build();
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder(editedEatery).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EATERY_SUCCESS,
                editedEatery.getName().fullName);

        Model expectedModel =
                new ModelManager(new EateryList(model.getEateryList()), model.getFeedList(), new UserPrefs());
        expectedModel.setEatery(model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased()), editedEatery);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY, new EditEateryDescriptor());
        Eatery editedEatery = model.getFilteredEateryList().get(INDEX_FIRST_EATERY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EATERY_SUCCESS,
                editedEatery.getName().fullName);

        Model expectedModel =
                new ModelManager(new EateryList(model.getEateryList()), model.getFeedList(), new UserPrefs());

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

        // edit eatery in filtered list into a duplicate in eatery list
        Eatery eateryInList = model.getEateryList().getEateryList().get(INDEX_SECOND_EATERY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EATERY,
                new EditEateryDescriptorBuilder(eateryInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EATERY);
    }

    @Test
    public void execute_invalidEateryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEateryList().size() + 1);
        EditEateryDescriptor descriptor = new EditEateryDescriptorBuilder().withName(VALID_NAME_NO_PREFIX_KFC).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of eatery list
     */
    @Test
    public void execute_invalidEateryIndexFilteredList_failure() {
        showEateryAtIndex(model, INDEX_FIRST_EATERY);
        Index outOfBoundIndex = INDEX_SECOND_EATERY;
        // ensures that outOfBoundIndex is still in bounds of eatery list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEateryList().getEateryList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditEateryDescriptorBuilder().withName(VALID_NAME_NO_PREFIX_KFC).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_EATERY, DESC_MAC);

        // same values -> returns true
        EditEateryDescriptor copyDescriptor = new EditEateryDescriptor(DESC_MAC);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_EATERY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_EATERY, DESC_MAC)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_EATERY, DESC_KFC)));
    }

}
