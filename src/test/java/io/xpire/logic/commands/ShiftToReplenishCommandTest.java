package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showXpireItemAtIndex;
import static io.xpire.logic.commands.util.CommandUtil.MESSAGE_DUPLICATE_ITEM_REPLENISH;
import static io.xpire.logic.commands.util.CommandUtil.MESSAGE_REPLENISH_SHIFT_SUCCESS;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_TENTH_ITEM;
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
import io.xpire.model.item.XpireItem;

public class ShiftToReplenishCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredXpireList_success() {
        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(INDEX_FIRST_ITEM);
        String expectedMessage = String.format(MESSAGE_REPLENISH_SHIFT_SUCCESS, xpireItemToShift.getName());
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.addItem(REPLENISH, xpireItemToShift.remodel());
        expectedModel.deleteItem(XPIRE, xpireItemToShift);
        assertCommandSuccess(shiftToReplenishCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredXpireList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(outOfBoundIndex);
        assertCommandFailure(shiftToReplenishCommand, model, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validIndexFilteredXpireList_success() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);

        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(MESSAGE_REPLENISH_SHIFT_SUCCESS, xpireItemToShift.getName());

        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.addItem(REPLENISH, xpireItemToShift.remodel());
        expectedModel.deleteItem(XPIRE, xpireItemToShift);

        assertCommandSuccess(shiftToReplenishCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredXpireListDuplicateItem_throwsException() {
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(INDEX_TENTH_ITEM);
        assertCommandFailure(shiftToReplenishCommand, model, MESSAGE_DUPLICATE_ITEM_REPLENISH);
    }

    @Test
    public void execute_validIndexFilteredXpireListDuplicateItem_throwsException() {
        showXpireItemAtIndex(model, INDEX_TENTH_ITEM);
        ShiftToReplenishCommand shiftToReplenishCommand = new ShiftToReplenishCommand(INDEX_FIRST_ITEM);
        assertCommandFailure(shiftToReplenishCommand, model, MESSAGE_DUPLICATE_ITEM_REPLENISH);
    }

    @Test
    public void equals() {
        ShiftToReplenishCommand shiftFirstCommand = new ShiftToReplenishCommand(INDEX_FIRST_ITEM);
        ShiftToReplenishCommand shiftSecondCommand = new ShiftToReplenishCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(shiftFirstCommand.equals(shiftFirstCommand));

        // same values -> returns true
        ShiftToReplenishCommand shiftFirstCommandCopy = new ShiftToReplenishCommand(INDEX_FIRST_ITEM);
        assertTrue(shiftFirstCommand.equals(shiftFirstCommand));

        // different types -> returns false
        assertFalse(shiftFirstCommand.equals(1));

        // null -> returns false
        assertFalse(shiftFirstCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(shiftFirstCommand.equals(shiftSecondCommand));

    }

}
