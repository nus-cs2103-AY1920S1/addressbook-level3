package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_RESET;
import static io.xpire.logic.commands.SetReminderCommand.MESSAGE_SUCCESS_SET;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_EIGHTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
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
import io.xpire.model.item.XpireItem;


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
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        ReminderThreshold validThreshold = new ReminderThreshold("2");
        SetReminderCommand setReminderCommand = new SetReminderCommand(outOfBoundIndex, validThreshold);
        assertCommandFailure(setReminderCommand, model, Messages.MESSAGE_INVALID_INDEX);
        //assertCommandFailure(setReminderCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_setReminder_success() {
        Index secondIndex = INDEX_SECOND_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("1");
        SetReminderCommand command = new SetReminderCommand(secondIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_SET, VALID_NAME_BANANA, threshold);
        XpireItem xpireItemToRemind = (XpireItem) model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        xpireItemToRemind.setReminderThreshold(threshold);
        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.setItem(XPIRE, xpireItemToRemind, xpireItemToRemind);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_disableReminder_success() {
        Index secondIndex = INDEX_SECOND_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("0");
        SetReminderCommand command = new SetReminderCommand(secondIndex, threshold);
        String expectedMessage = String.format(MESSAGE_SUCCESS_RESET, VALID_NAME_BANANA);
        XpireItem xpireItemToRemind = (XpireItem) model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        xpireItemToRemind.setReminderThreshold(threshold);
        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.setItem(XPIRE, xpireItemToRemind, xpireItemToRemind);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setReminderForExpiredItem_throwsCommandException() {
        Index eighthIndex = INDEX_EIGHTH_ITEM;
        ReminderThreshold threshold = new ReminderThreshold("0");
        SetReminderCommand command = new SetReminderCommand(eighthIndex, threshold);
        String expectedMessage = SetReminderCommand.MESSAGE_THRESHOLD_ITEM_EXPIRED;
        assertCommandFailure(command, model, expectedMessage);
    }
}
