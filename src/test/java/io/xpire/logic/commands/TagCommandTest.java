package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showReplenishItemAtIndex;
import static io.xpire.logic.commands.CommandTestUtil.showXpireItemAtIndex;
import static io.xpire.logic.commands.TagCommand.MESSAGE_TAG_ITEM_SUCCESS_TRUNCATION_WARNING;
import static io.xpire.logic.commands.TagCommand.MESSAGE_TOO_MANY_TAGS;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIFTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BAGEL;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_COOKIE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_CADBURY;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_COCOA;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_SWEET;
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
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;
import io.xpire.testutil.ItemBuilder;
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

    //---------------- Tests for Xpire List --------------------------------------------------------------------------
    @Test
    public void execute_validIndexUnfilteredXpireList_success() {
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
    public void execute_invalidIndexUnfilteredXpireList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        TagCommand tagCommand = new TagCommand(XPIRE, outOfBoundIndex,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredXpireList_success() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
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
        showSomeXpireItem(expectedModel, new ArrayList<>() {{
                add(expectedXpireItem);
            }});
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredXpireList_throwsCommandException() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLists()[0].getItemList().size());
        TagCommand tagCommand = new TagCommand(XPIRE, outOfBoundIndex,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //add tags to an already tagged xpireItem should add on more tags
    @Test
    public void execute_addMoreTagsToXpireItem_success() {
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

    @Test
    public void execute_truncateTagsInXpireItem_success() {
        XpireItem xpireItemToTag = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(XPIRE,
                INDEX_FIFTH_ITEM, new String[]{"Abcdefghijklmnopqrstuvwxyz"});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                .withQuantity(VALID_QUANTITY_JELLY)
                .withTags("Abcdefghijklmnopqrst", VALID_TAG_FRIDGE)
                .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                .build();
        String expectedMessage = String.format(MESSAGE_TAG_ITEM_SUCCESS_TRUNCATION_WARNING, expectedXpireItem);
        expectedModel.setItem(XPIRE, xpireItemToTag, expectedXpireItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //adding tags that already exist should not add duplicates or edit the existing tags
    @Test
    public void execute_addDuplicateTagsToXpireItem_success() {
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
    public void execute_showTagsInXpire_success() {
        TagCommand tagCommand = new TagCommand(XPIRE);
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.SHOW);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        List<String> tagList = new ArrayList<>();
        tagList.add((new Tag(VALID_TAG_PROTEIN)).toString());
        tagList.add((new Tag(VALID_TAG_FRIDGE)).toString());
        String expectedMessage = TagCommand.appendTagsToFeedback(tagList,
                new StringBuilder(TagCommand.MESSAGE_TAG_SHOW_SUCCESS)).toString();
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTagsInXpire_success() {
        TagCommand tagCommand = new TagCommand(XPIRE);
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.SHOW);
        ModelManager expectedModel = new ModelManager();
        assertCommandSuccess(tagCommand, expectedModel, TagCommand.MESSAGE_TAG_SHOW_FAILURE, expectedModel);
    }



    //---------------- Tests for Replenish List --------------------------------------------------------------------
    @Test
    public void execute_validIndexUnfilteredReplenishList_success() {
        model.setCurrentList(REPLENISH);
        Item replenishItemToTag = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(REPLENISH, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_SWEET});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_BAGEL)
                                                      .withTags(VALID_TAG_FRIDGE, VALID_TAG_SWEET)
                                                      .build();
        expectedModel.setItem(REPLENISH, replenishItemToTag, expectedReplenishItem);
        expectedModel.setCurrentList(REPLENISH);
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedReplenishItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredReplenishList_throwsCommandException() {
        model.setCurrentList(REPLENISH);
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        TagCommand tagCommand = new TagCommand(REPLENISH, outOfBoundIndex,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredReplenishList_success() {
        model.setCurrentList(REPLENISH);
        showReplenishItemAtIndex(model, INDEX_FIRST_ITEM);
        Item replenishItemToTag = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(REPLENISH, INDEX_FIRST_ITEM,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_BAGEL)
                                                            .withTags(VALID_TAG_FRIDGE, VALID_TAG_FRUIT)
                                                            .build();
        expectedModel.setItem(REPLENISH, replenishItemToTag, expectedReplenishItem);
        expectedModel.setCurrentList(REPLENISH);
        showSomeReplenishItem(expectedModel, new ArrayList<>() {{
                add(expectedReplenishItem);
            }}
        );
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedReplenishItem);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredReplenishList_throwsCommandException() {
        showReplenishItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLists()[1].getItemList().size());
        TagCommand tagCommand = new TagCommand(REPLENISH, outOfBoundIndex,
                new String[]{VALID_TAG_FRIDGE, VALID_TAG_FRUIT});
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //adding tags to an already tagged replenishItem should add on more tags
    @Test
    public void execute_addMoreTagsToReplenishItem_success() {
        model.setCurrentList(REPLENISH);
        Item replenishItemToTag = model.getCurrentList().get(INDEX_FOURTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(REPLENISH, INDEX_FOURTH_ITEM, new String[]{VALID_TAG_FRIDGE});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_COOKIE)
                                                            .withTags(VALID_TAG_FRIDGE, VALID_TAG_SWEET)
                                                            .build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedReplenishItem);
        expectedModel.setItem(REPLENISH, replenishItemToTag, expectedReplenishItem);
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //adding tags that already exist should not add duplicates or edit the existing tags
    @Test
    public void execute_addDuplicateTagsToReplenishItem_success() {
        model.setCurrentList(REPLENISH);
        Item replenishItemToTag = model.getCurrentList().get(INDEX_FOURTH_ITEM.getZeroBased());
        TagCommand tagCommand = new TagCommand(REPLENISH, INDEX_FOURTH_ITEM, new String[]{VALID_TAG_SWEET});
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.TAG);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_COOKIE)
                                                            .withTags(VALID_TAG_SWEET)
                                                            .build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_ITEM_SUCCESS, expectedReplenishItem);
        expectedModel.setItem(REPLENISH, replenishItemToTag, expectedReplenishItem);
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //model should not change, should return all tags in items
    @Test
    public void execute_showTagsInReplenishList_success() {
        TagCommand tagCommand = new TagCommand(REPLENISH);
        assertEquals(tagCommand.getMode(), TagCommand.TagMode.SHOW);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        List<String> tagList = new ArrayList<>();
        tagList.add((new Tag(VALID_TAG_CADBURY)).toString());
        tagList.add((new Tag(VALID_TAG_SWEET)).toString());
        tagList.add((new Tag(VALID_TAG_COCOA)).toString());
        String expectedMessage = TagCommand.appendTagsToFeedback(tagList,
                new StringBuilder(TagCommand.MESSAGE_TAG_SHOW_SUCCESS)).toString();
        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    //---------------- Failed tagging tests --------------------------------------------------------------------
    @Test
    public void execute_tooManyTags_throwsCommandException() {
        TagCommand tagCommand = new TagCommand(XPIRE, INDEX_FIFTH_ITEM,
                new String[]{VALID_TAG_CADBURY, VALID_TAG_PROTEIN,
                    VALID_TAG_COCOA, VALID_TAG_SWEET, VALID_TAG_FRUIT, VALID_TAG_FRIDGE});
        assertCommandFailure(tagCommand, model, MESSAGE_TOO_MANY_TAGS);
    }

    /**
     * Updates {@code model}'s filtered list to show xpireItems.
     */
    private void showSomeXpireItem(Model model, ArrayList<XpireItem> xpireItems) {
        model.filterCurrentList(XPIRE, xpireItems::contains);
    }

    /**
     * Updates {@code model}'s filtered list to show replenishItems.
     */
    private void showSomeReplenishItem(Model model, ArrayList<Item> replenishItems) {
        model.filterCurrentList(REPLENISH, replenishItems::contains);
    }

}
