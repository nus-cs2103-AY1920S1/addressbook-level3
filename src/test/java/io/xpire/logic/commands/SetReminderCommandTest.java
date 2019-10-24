package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_RESET;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_SET;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ReminderThreshold;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SetReminderCommand}.
 */
public class SetReminderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    }

    @Test
    public void equals() {

        SetReminderCommand setFirstCommand = new SetReminderCommand(INDEX_FIRST_ITEM, new ReminderThreshold("1"));
        SetReminderCommand setSecondCommand = new SetReminderCommand(INDEX_FIRST_ITEM, new ReminderThreshold("2"));

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns true
        SetReminderCommand setFirstCommandCopy = new SetReminderCommand(INDEX_FIRST_ITEM, new ReminderThreshold("1"));
        assertTrue(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        ReminderThreshold validThreshold = new ReminderThreshold("2");
        SetReminderCommand setReminderCommand = new SetReminderCommand(outOfBoundIndex, validThreshold);

        assertCommandFailure(setReminderCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_setReminder_success() {
        Index firstIndex = INDEX_FIRST_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("1");
        SetReminderCommand command = new SetReminderCommand(firstIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_SET, firstIndex.getOneBased(), threshold);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_disableReminder_success() {
        Index firstIndex = INDEX_FIRST_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("0");
        SetReminderCommand command = new SetReminderCommand(firstIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_RESET, firstIndex.getOneBased());
        assertCommandSuccess(command, model, expectedMessage, model);
    }
}
