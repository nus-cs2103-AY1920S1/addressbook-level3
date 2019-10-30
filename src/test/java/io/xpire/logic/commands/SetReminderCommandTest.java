package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_RESET;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_SET;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
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
        model = new ModelManager(getTypicalLists(), new UserPrefs());
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

        // different xpireItem -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredXpireItemList().size() + 1);
        ReminderThreshold validThreshold = new ReminderThreshold("2");
        SetReminderCommand setReminderCommand = new SetReminderCommand(outOfBoundIndex, validThreshold);

        assertCommandFailure(setReminderCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_setReminder_success() {
        Index secondIndex = INDEX_SECOND_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("1");
        SetReminderCommand command = new SetReminderCommand(secondIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_SET, secondIndex.getOneBased(), threshold);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_disableReminder_success() {
        Index secondIndex = INDEX_SECOND_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("0");
        SetReminderCommand command = new SetReminderCommand(secondIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_RESET, secondIndex.getOneBased());
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_setReminderForExpiredItem_throwsCommandException() {
        Index firstIndex = INDEX_FIRST_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("0");
        SetReminderCommand command = new SetReminderCommand(firstIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_RESET, firstIndex.getOneBased());
        assertCommandFailure(command, model, Messages.MESSAGE_THRESHOLD_ITEM_EXPIRED);
    }
}
