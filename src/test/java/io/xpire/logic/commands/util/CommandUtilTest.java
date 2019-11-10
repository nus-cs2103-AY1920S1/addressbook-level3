package io.xpire.logic.commands.util;

import static io.xpire.logic.commands.CommandTestUtil.showXpireItemAtIndex;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalIndexes.INDEX_TENTH_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_JELLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.item.exceptions.ItemNotFoundException;
import io.xpire.model.state.StackManager;
import io.xpire.model.state.StateManager;
import io.xpire.testutil.XpireItemBuilder;

public class CommandUtilTest {

    /**
     * Stub is used because quantity zero can only be created within Quantity class itself.
     */
    private static final Quantity QUANTITY_ZERO_STUB = new Quantity("1").deductQuantity(new Quantity("1"));
    private static final String QUANTITY_MAXIMUM_LIMIT = "100000";
    private Model model;
    private StateManager stateManager = new StackManager();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void reduceItemQuantity_success() throws Exception {
        // input quantity less than item quantity
        XpireItem itemToReduce = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                .withQuantity(VALID_QUANTITY_JELLY).build();
        XpireItem expectedItem = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                .withQuantity("3").build();
        assertEquals(expectedItem, CommandUtil.reduceItemQuantity(itemToReduce, new Quantity("1")));

        // input quantity equals to item quantity
        itemToReduce = new XpireItemBuilder().withName(VALID_NAME_DUCK)
                .withExpiryDate(VALID_EXPIRY_DATE_DUCK)
                .withQuantity(VALID_QUANTITY_DUCK).build();
        expectedItem = new XpireItemBuilder().withName(VALID_NAME_DUCK)
                .withExpiryDate(VALID_EXPIRY_DATE_DUCK).build();
        expectedItem.setQuantity(QUANTITY_ZERO_STUB);
        assertEquals(expectedItem, CommandUtil.reduceItemQuantity(itemToReduce, new Quantity("1")));
    }

    @Test
    public void reduceItemQuantity_throwsCommandException() {
        XpireItem itemToReduce = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                .withExpiryDate(VALID_EXPIRY_DATE_JELLY)
                .withQuantity(VALID_QUANTITY_JELLY).build();
        assertThrows(CommandException.class, () -> CommandUtil.reduceItemQuantity(itemToReduce, new Quantity("5")));
    }

    @Test
    public void increaseItemQuantity_success() throws CommandException {
        // new quantity is below maximum limit
        XpireItem itemToIncrease = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        XpireItem expectedItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity("6").build();
        assertEquals(expectedItem, CommandUtil.increaseItemQuantity(itemToIncrease, new Quantity("5")));

        // new quantity is maximum limit
        itemToIncrease = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        expectedItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(QUANTITY_MAXIMUM_LIMIT).build();
        assertEquals(expectedItem, CommandUtil.increaseItemQuantity(itemToIncrease, new Quantity("99999")));
    }

    @Test
    public void increaseItemQuantity_throwsCommandException() {
        XpireItem itemToIncrease = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        assertThrows(CommandException.class, () ->
                CommandUtil.increaseItemQuantity(itemToIncrease, new Quantity(QUANTITY_MAXIMUM_LIMIT)));
    }

    @Test
    public void retrieveItemFromUnfilteredList_success() {
        // item with exact same fields exist on list -> successful retrieval
        XpireItem expectedItem = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        XpireItem itemToRetrieve = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        assertEquals(expectedItem, CommandUtil.retrieveXpireItemFromList(itemToRetrieve, model.getCurrentList()));

        // item with exact same expiry date but different quantity exist on list -> successful retrieval
        expectedItem = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        itemToRetrieve = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_JELLY).build();
        assertEquals(expectedItem, CommandUtil.retrieveXpireItemFromList(itemToRetrieve, model.getCurrentList()));
    }

    @Test
    public void retrieveItemFromUnfilteredList_throwsItemNotFoundException() {
        // item not found in list
        XpireItem itemNotInList = new XpireItemBuilder().withName(VALID_NAME_CORIANDER)
                .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                .withQuantity(VALID_QUANTITY_CORIANDER).build();
        assertThrows(ItemNotFoundException.class, () ->
                CommandUtil.retrieveXpireItemFromList(itemNotInList, model.getCurrentList()));
    }

    @Test
    public void retrieveItemFromFilteredList_success() {
        // item with exact same fields -> successful retrieval
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        XpireItem expectedItem = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        XpireItem itemToRetrieve = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        assertEquals(expectedItem, CommandUtil.retrieveXpireItemFromList(itemToRetrieve, model.getCurrentList()));

        // item with exact same expiry date but different quantity exist on list -> successful retrieval
        expectedItem = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        itemToRetrieve = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_JELLY).build();
        assertEquals(expectedItem, CommandUtil.retrieveXpireItemFromList(itemToRetrieve, model.getCurrentList()));
    }

    @Test
    public void retrieveItemFromFilteredList_throwsItemNotFoundException() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        XpireItem itemNotInFilteredList = new XpireItemBuilder().withName(VALID_NAME_BANANA)
                .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
                .withQuantity(VALID_QUANTITY_BANANA).build();
        // item exists on unfiltered list
        assertTrue(model.hasItem(XPIRE, itemNotInFilteredList));
        assertThrows(ItemNotFoundException.class, () ->
                CommandUtil.retrieveXpireItemFromList(itemNotInFilteredList, model.getCurrentList()));
    }

    @Test
    public void shiftItemToReplenishList_unfilteredList_success() throws CommandException {
        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.addItem(REPLENISH, xpireItemToShift.remodel());
        expectedModel.deleteItem(XPIRE, xpireItemToShift);
        CommandUtil.shiftItemToReplenishList(stateManager, model, xpireItemToShift);
        assertEquals(expectedModel, model);
    }

    @Test
    public void shiftItemToReplenishList_unfilteredList_throwsCommandException() {
        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_TENTH_ITEM.getZeroBased());
        assertTrue(model.hasItem(REPLENISH, xpireItemToShift.remodel()));
        assertThrows(CommandException.class, () -> CommandUtil.shiftItemToReplenishList(stateManager, model,
                xpireItemToShift));
    }

    @Test
    public void shiftItemToReplenishList_filteredList_success() throws CommandException {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        expectedModel.addItem(REPLENISH, xpireItemToShift.remodel());
        expectedModel.deleteItem(XPIRE, xpireItemToShift);
        CommandUtil.shiftItemToReplenishList(stateManager, model, xpireItemToShift);
        assertEquals(expectedModel, model);
    }

    @Test
    public void shiftItemToReplenishList_filteredList_throwsCommandException() {
        showXpireItemAtIndex(model, INDEX_TENTH_ITEM);
        XpireItem xpireItemToShift = (XpireItem) model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased());
        assertTrue(model.hasItem(REPLENISH, xpireItemToShift.remodel()));
        assertThrows(CommandException.class, () -> CommandUtil.shiftItemToReplenishList(stateManager, model,
                xpireItemToShift));
    }

    @Test
    public void updateItemQuantity_success() throws CommandException {
        XpireItem itemToUpdate = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(VALID_QUANTITY_APPLE).build();
        CommandUtil.updateItemQuantity(stateManager, model, itemToUpdate);
        ModelManager expectedModel = new ModelManager(model.getLists(), new UserPrefs());
        XpireItem expectedItem = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity("2").build();
        expectedModel.setItem(XPIRE, model.getCurrentList().get(INDEX_FIRST_ITEM.getZeroBased()), expectedItem);
        assertEquals(expectedModel, model);
    }

    @Test
    public void updateItemQuantity_throwsCommandException() {
        // input quantity is invalid
        XpireItem itemToUpdate = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
                .withQuantity(QUANTITY_MAXIMUM_LIMIT).build();
        assertThrows(CommandException.class, () -> CommandUtil.updateItemQuantity(stateManager, model, itemToUpdate));

        //item does not exist
        XpireItem nonExistentItem = new XpireItemBuilder().withName(VALID_NAME_CORIANDER)
                .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                .withQuantity(VALID_QUANTITY_CORIANDER).build();
        assertThrows(ItemNotFoundException.class, () ->
                CommandUtil.retrieveXpireItemFromList(nonExistentItem, model.getCurrentList()));
    }
}
