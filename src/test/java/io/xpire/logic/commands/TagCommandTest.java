package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showItemAtIndex;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIFTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;
import io.xpire.testutil.XpireItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TagCommand}.
 */
public class TagCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                                             .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .build();
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        TagCommand tagCommand = new TagCommand(XPIRE, outOfBoundIndex, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                                             .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .build();
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        showSomeItem(expectedModel, new ArrayList<>() {{
                add(expectedXpireItem);
            }});
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLists()[0].getItemList().size());
        TagCommand tagCommand = new TagCommand(XPIRE, outOfBoundIndex, new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //add tags to an already tagged xpireItem should add on more tags
    @Test
    public void execute_addMoreTags_success() {
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIFTH_ITEM, new String[]{VALID_TAG_FRUIT});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                                             .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                                             .withQuantity(VALID_QUANTITY_JELLY)
                                             .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                             .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                             .build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedXpireItem);
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //adding tags that already exist should not add duplicates or edit the existing tags
    @Test
    public void execute_addDuplicateTags_success() {
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIFTH_ITEM, new String[]{VALID_TAG_FRIDGE});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                .withQuantity(VALID_QUANTITY_JELLY)
                .withTags(VALID_TAG_FRIDGE)
                .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                .build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedXpireItem);
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //model should not change, should return all tags in items
    @Test
    public void execute_showTags_success() {
        TagCommand tagCommand = new TagCommand(XPIRE);
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.SHOW);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        List<String> tagList = new ArrayList<>();
        tagList.add((new Tag(VALID_TAG_FRIDGE)).toString());
        tagList.add((new Tag(VALID_TAG_PROTEIN)).toString());
        String expectedMessage = TagCommand.appendTagsToFeedback(tagList,
                new StringBuilder(TagCommand.MESSAGE_TAG_SHOW_SUCCESS)).toString();
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show xpireItems.
     */
    private void showSomeItem(Model model, ArrayList<XpireItem> xpireItems) {
        model.filterCurrentList(XPIRE, xpireItems::contains);
    }
}
