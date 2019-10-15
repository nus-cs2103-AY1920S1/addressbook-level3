package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_NAME_JELLY;
import static io.xpire.logic.commands.CommandTestUtil.VALID_QUANTITY_JELLY;
import static io.xpire.logic.commands.CommandTestUtil.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_FRIDGE;
import static io.xpire.logic.commands.CommandTestUtil.VALID_TAG_FRUIT;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showItemAtIndex;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SEVENTH_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;

import io.xpire.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TagCommand}.
 * TODO: Implement Tests
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToTag = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_ITEM, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});

        ModelManager expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        Item expectedItem = new ItemBuilder().withName(VALID_NAME_APPLE)
                                             .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .build();
        expectedModel.setItem(itemToTag, expectedItem);
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Item itemToTag = model.getFilteredItemList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_ITEM, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});

        ModelManager expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        Item expectedItem = new ItemBuilder().withName(VALID_NAME_APPLE)
                                             .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .build();
        expectedModel.setItem(itemToTag, expectedItem);
        showSomeItem(expectedModel, new ArrayList<>() {{
                add(expectedItem);
            }});
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getXpire().getItemList().size());
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addMoreTags_success() {
        Item itemToTag = model.getFilteredItemList().get(INDEX_SEVENTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(INDEX_SEVENTH_ITEM, new String[]{VALID_TAG_FRUIT});

        ModelManager expectedModel = new ModelManager(model.getXpire(), new UserPrefs());
        Item expectedItem = new ItemBuilder().withName(VALID_NAME_JELLY)
                                             .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                                             .withQuantity(VALID_QUANTITY_JELLY)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                             .build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedItem);
        expectedModel.setItem(itemToTag, expectedItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show items.
     */
    private void showSomeItem(Model model, ArrayList<Item> items) {
        model.updateFilteredItemList(items::contains);
    }
}
