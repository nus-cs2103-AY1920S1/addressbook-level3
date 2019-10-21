package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static com.typee.testutil.TypicalIndexes.INDEX_FIRST_ENGAGEMENT;
import static com.typee.testutil.TypicalIndexes.INDEX_SECOND_ENGAGEMENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.commons.core.Messages;
import com.typee.commons.core.index.Index;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.model.engagement.Engagement;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Engagement engagementToDelete = model.getFilteredEngagementList().get(INDEX_FIRST_ENGAGEMENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ENGAGEMENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, engagementToDelete);

        ModelManager expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());

        expectedModel.deleteEngagement(engagementToDelete);
        expectedModel.saveEngagementList();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEngagementList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showEngagementAtIndex(model, INDEX_FIRST_ENGAGEMENT);
        Engagement engagementToDelete = model.getFilteredEngagementList().get(INDEX_FIRST_ENGAGEMENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ENGAGEMENT);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, engagementToDelete);
        Model expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
        expectedModel.deleteEngagement(engagementToDelete);
        expectedModel.saveEngagementList();
        showNoEngagement(expectedModel);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showEngagementAtIndex(model, INDEX_FIRST_ENGAGEMENT);

        Index outOfBoundIndex = INDEX_SECOND_ENGAGEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEngagementList().getEngagementList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ENGAGEMENT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ENGAGEMENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ENGAGEMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no engagements.
     */
    private void showNoEngagement(Model model) {

        model.updateFilteredEngagementList(p -> false);
        assertTrue(model.getFilteredEngagementList().isEmpty());
    }
}
