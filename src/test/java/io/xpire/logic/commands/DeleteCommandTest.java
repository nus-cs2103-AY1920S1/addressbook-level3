package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_REPLENISH_SHIFT_SUCCESS;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandFailure;
import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showReplenishItemAtIndex;
import static io.xpire.logic.commands.CommandTestUtil.showXpireItemAtIndex;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIFTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_FOURTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SEVENTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_SIXTH_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_EXPIRED_MILK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BISCUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CHOCOLATE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_COOKIE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EXPIRED_MILK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_CADBURY;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_COCOA;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_DRINK;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRUIT;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_SWEET;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.testutil.ItemBuilder;
import io.xpire.testutil.XpireItemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    //---------------- Tests for Xpire List --------------------------------------------------------------------------
    @Test
    public void execute_validIndexUnfilteredXpireList_success() {
        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, xpireItemToDelete);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.deleteItem(XPIRE, xpireItemToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredXpireList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredXpireList_success() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);

        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, xpireItemToDelete);

        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.deleteItem(XPIRE, xpireItemToDelete);
        showNoItem(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredXpireList_throwsCommandException() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of xpire main tracking list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLists()[0].getItemList().size());

        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //test to delete tags for xpireItem with tags
    @Test
    public void execute_deleteTagsFromXpireItemNotAllFields_success() {
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_THIRD_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRIDGE));
        set.add(new Tag(VALID_TAG_PROTEIN));
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_THIRD_ITEM, set);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_DUCK)
                                             .withExpiryDate(VALID_EXPIRY_DATE_DUCK)
                                             .build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedXpireItem);
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem); //set target xpireItem with no tags
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //Tags don't exist for you to delete.
    @Test
    public void execute_deleteTagsFromXpireItemNotAllFields_throwsCommandException() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_DRINK));
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_SIXTH_ITEM, set);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAGS);
    }

    //test to delete tags for xpireItem with all fields present
    @Test
    public void execute_deleteTagsFromXpireItemAllFields_success() {
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRIDGE));

        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIFTH_ITEM, set);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());

        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                                             .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                                             .withQuantity(VALID_QUANTITY_JELLY)
                                             .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                             .build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedXpireItem);
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem); //set target xpireItem with no tags
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //test that does not delete any tags due to empty set
    @Test
    public void execute_deleteNoTagsFromXpireItemAllFields_success() {
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_FIFTH_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_FIFTH_ITEM, set);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                                             .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                                             .withQuantity(VALID_QUANTITY_JELLY)
                                             .withTags(VALID_TAG_FRIDGE)
                                             .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                             .build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedXpireItem);
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem); //set target xpireItem with no tags
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsFromXpireItemAllFields_throwsCommandException() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRUIT));
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_SEVENTH_ITEM, set);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAGS);
    }

    @Test
    public void execute_deleteQuantityLessThanXpireItemQuantityFromXpireItem_success() {
        //All xpireItem fields present
        XpireItem targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        Quantity quantityToDeduct = new Quantity("2");
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_SECOND_ITEM, quantityToDeduct);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_BANANA)
                .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
                .withQuantity("3")
                .withReminderThreshold(VALID_REMINDER_THRESHOLD_BANANA)
                .build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUANTITY_SUCCESS,
                quantityToDeduct.toString(), targetXpireItem);
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        //Not all xpireItem fields present
        targetXpireItem = (XpireItem) model.getCurrentList().get(INDEX_SIXTH_ITEM.getZeroBased());
        quantityToDeduct = new Quantity("1");
        deleteCommand = new DeleteCommand(XPIRE, INDEX_SIXTH_ITEM, quantityToDeduct);
        expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedXpireItem = new XpireItemBuilder().withName(VALID_NAME_EXPIRED_MILK)
                .withExpiryDate(VALID_EXPIRY_DATE_EXPIRED_MILK)
                .withQuantity("1")
                .build();
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUANTITY_SUCCESS,
                quantityToDeduct.toString(), targetXpireItem);
        expectedModel.setItem(XPIRE, targetXpireItem, expectedXpireItem);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteQuantityEqualsToXpireItemQuantityFromXpireItem_success() {
        Quantity quantityToDeduct = new Quantity("1");
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_THIRD_ITEM, quantityToDeduct);
        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_THIRD_ITEM.getZeroBased());
        Name itemName = xpireItemToDelete.getName();
        Set<Tag> itemTags = xpireItemToDelete.getTags();
        Item adaptedItem = new Item(itemName, itemTags);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUANTITY_SUCCESS,
                quantityToDeduct.toString(), xpireItemToDelete) + "\n"
                + String.format(MESSAGE_REPLENISH_SHIFT_SUCCESS, xpireItemToDelete.getName());
        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.deleteItem(XPIRE, xpireItemToDelete);
        expectedModel.addItem(REPLENISH, adaptedItem);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteQuantityMoreThanXpireItemQuantityFromXpireItem_throwsCommandException() {
        XpireItem xpireItemToDelete = (XpireItem) model.getCurrentList().get(INDEX_THIRD_ITEM.getZeroBased());
        Quantity quantityToDeduct = new Quantity("3");
        DeleteCommand deleteCommand = new DeleteCommand(XPIRE, INDEX_THIRD_ITEM, quantityToDeduct);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_DELETE_QUANTITY_FAILURE);
    }

    //---------------- Tests for Replenish List -----------------------------------------------------------------------
    @Test
    public void execute_validIndexUnfilteredReplenishList_success() {
        model.setCurrentList(REPLENISH);
        Item replenishItemToDelete = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, replenishItemToDelete);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        expectedModel.deleteItem(REPLENISH, replenishItemToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredReplenishList_throwsCommandException() {
        model.setCurrentList(REPLENISH);
        Index outOfBoundIndex = Index.fromOneBased(model.getCurrentList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredReplenishList_success() {
        model.setCurrentList(REPLENISH);
        showReplenishItemAtIndex(model, INDEX_FIRST_ITEM);
        Item replenishItemToDelete = model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ITEM_SUCCESS, replenishItemToDelete);
        Model expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        expectedModel.deleteItem(REPLENISH, replenishItemToDelete);
        showNoItem(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredReplenishList_throwsCommandException() {
        model.setCurrentList(REPLENISH);
        showReplenishItemAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of replenish list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLists()[1].getItemList().size());

        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    //test to delete tags for replenishItem with tags
    @Test
    public void execute_deleteTagsFromReplenishItemNotAllFields_success() {
        model.setCurrentList(REPLENISH);
        Item targetReplenishItem = model.getCurrentList().get(INDEX_SECOND_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_SWEET));
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_SECOND_ITEM, set);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.setCurrentList(REPLENISH);
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_BISCUIT).build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedReplenishItem);
        //set target replenishItem with no tags
        expectedModel.setItem(REPLENISH, targetReplenishItem, expectedReplenishItem);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //Tags don't exist for you to delete.
    @Test
    public void execute_deleteTagsFromReplenishItemNotAllFields_throwsCommandException() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_DRINK));
        model.setCurrentList(REPLENISH);
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_SECOND_ITEM, set);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAGS);
    }

    //test to delete tags for replenishItem with all fields present
    @Test
    public void execute_deleteTagsFromReplenishItemAllFields_success() {
        model.setCurrentList(REPLENISH);
        Item targetReplenishItem = model.getCurrentList().get(INDEX_FOURTH_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_SWEET));
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FOURTH_ITEM, set);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_COOKIE).build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedReplenishItem);
        //set target replenish Item with no tags
        expectedModel.setItem(REPLENISH, targetReplenishItem, expectedReplenishItem);
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //test that does not delete any tags due to empty set
    @Test
    public void execute_deleteNoTagsFromReplenishItemAllFields_success() {
        model.setCurrentList(REPLENISH);
        Item targetReplenishItem = model.getCurrentList().get(INDEX_THIRD_ITEM.getZeroBased());
        Set<Tag> set = new TreeSet<>(new TagComparator());
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_THIRD_ITEM, set);

        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        Item expectedReplenishItem = new ItemBuilder().withName(VALID_NAME_CHOCOLATE)
                                                            .withTags(VALID_TAG_CADBURY, VALID_TAG_COCOA)
                                                            .build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAGS_SUCCESS, expectedReplenishItem);
        expectedModel.setItem(REPLENISH, targetReplenishItem, expectedReplenishItem); //no change in item
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsFromReplenishItemAllFields_throwsCommandException() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag(VALID_TAG_FRUIT));
        DeleteCommand deleteCommand = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM, set);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAGS);
    }

    //deletion of quantity test cases omitted as replenish items have no quantity fields

    @Test
    public void equals() {
        //---------------- Tests for Xpire List ----------------------------------------------------------------
        DeleteCommand deleteXpireFirstCommand = new DeleteCommand(XPIRE, INDEX_FIRST_ITEM);
        DeleteCommand deleteXpireSecondCommand = new DeleteCommand(XPIRE, INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteXpireFirstCommand.equals(deleteXpireFirstCommand));

        // same values -> returns true
        DeleteCommand deleteXpireFirstCommandCopy = new DeleteCommand(XPIRE, INDEX_FIRST_ITEM);
        assertTrue(deleteXpireFirstCommand.equals(deleteXpireFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteXpireFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteXpireFirstCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(deleteXpireFirstCommand.equals(deleteXpireSecondCommand));

        //---------------- Tests for Replenish List ----------------------------------------------------------------
        DeleteCommand deleteReplenishListFirstCommand = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM);
        DeleteCommand deleteReplenishListSecondCommand = new DeleteCommand(REPLENISH, INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteReplenishListFirstCommand.equals(deleteReplenishListFirstCommand));

        // same values -> returns true
        DeleteCommand deleteReplenishListFirstCommandCopy = new DeleteCommand(REPLENISH, INDEX_FIRST_ITEM);
        assertTrue(deleteReplenishListFirstCommand.equals(deleteReplenishListFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteReplenishListFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteReplenishListFirstCommand.equals(null));

        // different xpireItem -> returns false
        assertFalse(deleteReplenishListFirstCommand.equals(deleteReplenishListSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     **/
    private void showNoItem(Model model) {
        model.filterCurrentList(XPIRE, p -> false);
        assertTrue(model.getCurrentList().isEmpty());

        model.filterCurrentList(REPLENISH, p -> false);
        assertTrue(model.getCurrentList().isEmpty());
    }

}
