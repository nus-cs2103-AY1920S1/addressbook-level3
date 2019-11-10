package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.testutil.TypicalItems.APPLE;
import static io.xpire.testutil.TypicalItems.GRAPE;
import static io.xpire.testutil.TypicalItems.HONEY;
import static io.xpire.testutil.TypicalItems.ICE_CREAM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ReminderThresholdExceededPredicate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CheckCommand}.
 */
public class CheckCommandTest {

    private Model model = new ModelManager(getTypicalLists(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());

    @Test
    public void execute_checkReminder_success() {
        ReminderThresholdExceededPredicate predicate = new ReminderThresholdExceededPredicate();
        CheckCommand command = new CheckCommand(predicate);
        String expectedMessage = command.getMessage();
        expectedModel.filterCurrentList(ListType.XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GRAPE, HONEY, ICE_CREAM),
                model.getCurrentList());
    }

    @Test
    public void execute_checkDays_success() {
        ExpiringSoonPredicate predicate = new ExpiringSoonPredicate(20);
        CheckCommand command = new CheckCommand(predicate, 20);
        String expectedMessage = command.getMessage();
        expectedModel.filterCurrentList(ListType.XPIRE, predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(APPLE, GRAPE, HONEY, ICE_CREAM), model.getCurrentList());
    }

    @Test
    public void equals() {
        CheckCommand checkReminderCommand = new CheckCommand(new ReminderThresholdExceededPredicate());
        CheckCommand checkDaysCommand = new CheckCommand(new ExpiringSoonPredicate(5), 5);

        // same object -> returns true
        assertTrue(checkReminderCommand.equals(checkReminderCommand));

        // same values -> returns true
        CheckCommand checkDaysCommandCopy = new CheckCommand(new ExpiringSoonPredicate(5), 5);
        assertTrue(checkDaysCommand.equals(checkDaysCommandCopy));

        // different types -> returns false
        assertFalse(checkReminderCommand.equals(1));

        // null -> returns false
        assertFalse(checkReminderCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(checkReminderCommand.equals(checkDaysCommand));
    }
}
