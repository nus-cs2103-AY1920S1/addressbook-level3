package seedu.address.cashier.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.cashier.model.ModelManager.onCashierMode;
import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;
import static seedu.address.testutil.TypicalItem.BURGER_AND_CHIPS;
import static seedu.address.testutil.TypicalItem.CHIPS;
import static seedu.address.testutil.TypicalItem.FISH_BURGER;
import static seedu.address.testutil.TypicalItem.PHONE_CASE;
import static seedu.address.testutil.TypicalItem.STORYBOOK;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.model.exception.NoItemToCheckoutException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

public class ModelManagerTest {

    private static final String SALES_DESCRIPTION = "Items sold";
    private static final String SALES_CATEGORY = "Sales";
    private ModelManager modelManager = new ModelManager();


    public void setInventoryList() {
        ArrayList<Item> list = new ArrayList<>();
        list.add(FISH_BURGER);
        list.add(STORYBOOK);
        list.add(CHIPS);
        list.add(PHONE_CASE);
        InventoryList inventoryList = new InventoryList(list);
    }

    public void setInventoryList2() {
        ArrayList<Item> list = new ArrayList<>();
        list.add(BURGER_AND_CHIPS);
        list.add(FISH_BURGER);
        list.add(CHIPS);
        list.add(STORYBOOK);
        InventoryList inventoryList = new InventoryList(list);
    }

    @Test
    public void constructor() {
        assertEquals(new InventoryList(), new InventoryList(modelManager.getInventoryList().getiArrayList()));
        assertEquals(new TransactionList(), new TransactionList(modelManager.getTransactionList().getTarrList()));
    }

    @Test
    public void hasSufficientQuantityToAdd_returnTrue() throws NoSuchItemException {
        modelManager.clearSalesList();
        setInventoryList2();
        assertTrue(modelManager.hasSufficientQuantityToAdd(BURGER_AND_CHIPS.getDescription(), 3));
        modelManager.clearSalesList();
    }

    @Test
    public void hasSufficientQuantityToAdd_invalidQuantity_returnFalse() throws NoSuchItemException {
        setInventoryList();

        Item i = new ItemBuilder().withQuantity(5).build();
        modelManager.addItem(i);
        assertFalse(modelManager.hasSufficientQuantityToAdd(FISH_BURGER.getDescription(),
                FISH_BURGER.getQuantity()));
        modelManager.clearSalesList();
    }


    @Test
    public void hasSufficientQuantityToEdit_returnTrue() throws NoSuchItemException {
        setInventoryList();
        modelManager.addItem(CHIPS);
        assertTrue(modelManager.hasSufficientQuantityToEdit(1, 3));
        modelManager.clearSalesList();
    }

    @Test
    public void hasSufficientQuantityToEdit_invalidQuantity_returnFalse() throws NoSuchItemException {
        setInventoryList();
        modelManager.addItem(FISH_BURGER);
        assertFalse(modelManager.hasSufficientQuantityToEdit(1, 200000));
        modelManager.clearSalesList();
    }

    @Test
    public void getStockLeft_validDescription_successful() throws NoSuchItemException {
        setInventoryList();
        assertEquals(FISH_BURGER.getQuantity(), modelManager.getStockLeft(FISH_BURGER.getDescription()));
        modelManager.clearSalesList();
    }

    @Test
    public void getStockLeft_invalidDescription_failure() {
        // if item is not in inventory
        assertThrows(NoSuchItemException.class, () -> modelManager.getStockLeft(PHONE_CASE.getDescription()));
    }

    @Test
    public void hasItemInInventoryByItem_validItem_returnTrue() {
        setInventoryList();
        assertTrue(modelManager.hasItemInInventory(FISH_BURGER));
    }

    @Test
    public void hasItemInInventoryByItem_invalidItem_returnFalse() {
        // if item is not in inventory
        assertFalse(modelManager.hasItemInInventory(PHONE_CASE));
    }

    @Test
    public void hasItemInInventoryByDescription_validDescription_returnTrue() {
        setInventoryList();
        assertTrue(modelManager.hasItemInInventory(FISH_BURGER.getDescription()));
    }

    @Test
    public void hasItemInInventoryByDescription_invalidItem_returnFalse() {
        // if item is not in inventory
        assertFalse(modelManager.hasItemInInventory(PHONE_CASE.getDescription()));
    }

    @Test
    public void addItemBySimilarItem_validItem_successful() {
        setInventoryList();
        modelManager.addItem(FISH_BURGER);
        assertEquals(1, modelManager.getSalesList().size());
        assertEquals(FISH_BURGER, modelManager.findItemByIndex(1));
        modelManager.clearSalesList();
    }

    @Test
    public void addItemByDescription_validItem_successful() throws NoSuchItemException {
        setInventoryList();
        int qtyAdded = 5;

        modelManager.addItem(FISH_BURGER.getDescription(), qtyAdded);
        assertEquals(1, modelManager.getSalesList().size());
        Item itemAdded = modelManager.findItemByIndex(1);
        assertEquals(qtyAdded, itemAdded.getQuantity());
        modelManager.clearSalesList();
    }

    @Test
    public void addItemByDescription_existingSimilarItem_successful() throws NoSuchItemException {
        setInventoryList();
        int qtyAdded = 5;

        modelManager.addItem(FISH_BURGER.getDescription(), qtyAdded);
        modelManager.addItem(FISH_BURGER.getDescription(), 3);
        assertEquals(1, modelManager.getSalesList().size());
        Item itemAdded = modelManager.findItemByIndex(1);
        assertEquals(8, itemAdded.getQuantity());
        modelManager.clearSalesList();
    }

    @Test
    public void addItemByDescription_invalidItem_failure() {
        assertThrows(NoSuchItemException.class, () -> modelManager.addItem(PHONE_CASE.getDescription(), 5));
    }

    @Test
    public void findItemByIndex_validIndex_successful() {
        setInventoryList();
        modelManager.addItem(FISH_BURGER);

        Item itemFound = modelManager.findItemByIndex(1);
        assertEquals(itemFound, FISH_BURGER);
        modelManager.clearSalesList();
    }

    @Test
    public void findItemByDescription_validDescription_successful() throws NoSuchItemException {
        setInventoryList();
        modelManager.addItem(CHIPS);
        modelManager.addItem(STORYBOOK);

        int indexFound = modelManager.findIndexByDescription(STORYBOOK.getDescription());
        // index is zero-based
        assertEquals(1, indexFound);
        modelManager.clearSalesList();
    }

    @Test
    public void findItemByDescription_invalidDescription_failure() {
        setInventoryList();
        modelManager.addItem(CHIPS);
        modelManager.addItem(STORYBOOK);

        assertThrows(NoSuchItemException.class, () -> modelManager.findIndexByDescription(
                FISH_BURGER.getDescription()));
        modelManager.clearSalesList();
    }

    @Test
    public void deleteItem_successful() {
        setInventoryList();
        modelManager.addItem(CHIPS);
        modelManager.addItem(STORYBOOK);
        modelManager.addItem(FISH_BURGER);

        assertEquals(3, modelManager.getSalesList().size());
        modelManager.deleteItem(2);
        assertEquals(2, modelManager.getSalesList().size());
        assertEquals(CHIPS, modelManager.findItemByIndex(1));
        assertEquals(FISH_BURGER, modelManager.findItemByIndex(2));
        modelManager.clearSalesList();
    }

    @Test
    public void setItem_successful() throws Exception {
        setInventoryList();
        modelManager.addItem(STORYBOOK);
        modelManager.addItem(CHIPS);

        Item item = new ItemBuilder().withDescription("new chips").build();

        modelManager.setItem(1, item);
        // index in findItemByIndex() is one-based
        assertEquals(item, modelManager.findItemByIndex(2));
        modelManager.clearSalesList();
    }

    @Test
    public void updateInventoryList_successful() throws Exception {
        setInventoryList();
        modelManager.addItem(CHIPS.getDescription(), 3);
        modelManager.addItem(STORYBOOK.getDescription(), 5);

        int originalChipQty = CHIPS.getQuantity();
        int originalStorybookQty = STORYBOOK.getQuantity();

        modelManager.updateInventoryList();
        ArrayList<Item> expectedList = new ArrayList<>();
        expectedList.add(new ItemBuilder()
                .withDescription(CHIPS.getDescription())
                .withQuantity(originalChipQty - 3).build());
        expectedList.add(new ItemBuilder()
                .withDescription(STORYBOOK.getDescription())
                .withQuantity(originalStorybookQty - 5).build());
        expectedList.add(FISH_BURGER);
        expectedList.add(PHONE_CASE);

        assertEquals(expectedList.size(), modelManager.getInventoryList().size());
        for (int i = 0; i < expectedList.size(); i++) {
            InventoryList inventoryList = modelManager.getInventoryList();
            String description = expectedList.get(i).getDescription();
            assertEquals(expectedList.get(i).getQuantity(), inventoryList.getOriginalItem(description).getQuantity());
        }
        modelManager.clearSalesList();
    }

    @Test
    public void setCashier_successful() throws NoCashierFoundException {
        Person p = new PersonBuilder().build();
        modelManager.setCashier(p);
        assertEquals(p, modelManager.getCashier());
    }

    @Test
    public void getCashier_nullCashier_throwsNoCashierFoundException() {
        assertThrows(NoCashierFoundException.class, () -> modelManager.getCashier());
    }

    @Test
    public void getTotalAmount_successful() throws AmountExceededException {
        setInventoryList();
        Item chips = new ItemBuilder()
                .withDescription(CHIPS.getDescription())
                .withQuantity(3)
                .withPrice(CHIPS.getPrice())
                .build();
        Item storybook = new ItemBuilder()
                .withDescription(STORYBOOK.getDescription())
                .withQuantity(5)
                .withPrice(STORYBOOK.getPrice())
                .build();
        modelManager.addItem(chips);
        modelManager.addItem(storybook);
        assertEquals(chips.getSubtotal() + storybook.getSubtotal(),
                Double.parseDouble(DECIMAL_FORMAT.format(modelManager.getTotalAmount())));
        modelManager.clearSalesList();

    }

    @Test
    public void getTotalAmount_throwsAmountExceededException() {
        setInventoryList();
        Item chips = new ItemBuilder()
                .withDescription(CHIPS.getDescription())
                .withQuantity(99999399)
                .withPrice(CHIPS.getPrice())
                .build();
        Item storybook = new ItemBuilder()
                .withDescription(STORYBOOK.getDescription())
                .withQuantity(999999999)
                .withPrice(STORYBOOK.getPrice())
                .build();
        modelManager.addItem(chips);
        modelManager.addItem(storybook);
        assertThrows(AmountExceededException.class, () -> modelManager.getTotalAmount());
        modelManager.clearSalesList();
    }

    @Test
    public void getSalesList_successful() {
        setInventoryList();
        modelManager.addItem(CHIPS);
        modelManager.addItem(FISH_BURGER);
        assertEquals(2, modelManager.getSalesList().size());
        assertEquals(CHIPS, modelManager.getSalesList().get(0));
        assertEquals(FISH_BURGER, modelManager.getSalesList().get(1));
        modelManager.clearSalesList();
    }

    @Test
    public void clearSalesList_successful() {
        setInventoryList();
        modelManager.addItem(FISH_BURGER);
        modelManager.addItem(CHIPS);
        assertEquals(2, modelManager.getSalesList().size());
        modelManager.clearSalesList();
        assertEquals(0, modelManager.getSalesList().size());
    }

    @Test
    public void resetCashier_throwsNoCashierFoundException() throws NoCashierFoundException {
        Person p = new PersonBuilder().build();
        modelManager.setCashier(p);
        assertEquals(p, modelManager.getCashier());
        modelManager.resetCashier();
        assertThrows(NoCashierFoundException.class, () -> modelManager.getCashier());
    }

    @Test
    public void editItem_successful() throws NoSuchItemException {
        setInventoryList();
        modelManager.addItem(FISH_BURGER);
        int expectedQty = 3;
        modelManager.editItem(1, expectedQty);
        Item editedItem = modelManager.getSalesList().get(0);
        assertEquals(expectedQty, editedItem.getQuantity());
        modelManager.clearSalesList();
    }

    @Test
    public void isSellable_notForSaleItem_assertFalse() throws NoSuchItemException {
        setInventoryList();
        assertFalse(modelManager.isSellable(PHONE_CASE.getDescription()));
    }

    @Test
    public void getDescriptionByCategory_successful() {
        setInventoryList();
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(FISH_BURGER.getDescription());
        expectedList.add(CHIPS.getDescription());

        ArrayList<String> actualList = modelManager.getDescriptionByCategory("food");
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
        modelManager.clearSalesList();
    }

    @Test
    public void getRecommendedItem_successful() throws NoSuchIndexException {
        setInventoryList2();
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(BURGER_AND_CHIPS.getDescription());
        expectedList.add(FISH_BURGER.getDescription());

        // incomplete description
        ArrayList<String> actualList = modelManager.getRecommendedItems("burg");
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
        modelManager.clearSalesList();
    }

    @Test
    public void getRecommendedItemThatEndSimilarly_successful() throws NoSuchIndexException {
        setInventoryList2();
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(BURGER_AND_CHIPS.getDescription());
        expectedList.add(CHIPS.getDescription());

        // incomplete description
        ArrayList<String> actualList = modelManager.getRecommendedItems("chips");
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < actualList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
        modelManager.clearSalesList();
    }

    @Test
    public void checkoutAsTransaction_successful() throws NoItemToCheckoutException, AmountExceededException {
        setInventoryList();
        Person p = new PersonBuilder().build();
        modelManager.setCashier(p);

        Item storybook = new ItemBuilder()
                .withDescription(STORYBOOK.getDescription())
                .withQuantity(5)
                .withPrice(STORYBOOK.getPrice())
                .build();

        Item chips = new ItemBuilder()
                .withDescription(CHIPS.getDescription())
                .withQuantity(3)
                .withPrice(CHIPS.getPrice())
                .build();

        modelManager.addItem(chips);
        modelManager.addItem(storybook);
        double totalAmount = modelManager.getTotalAmount();
        Transaction transaction = modelManager.checkoutAsTransaction(totalAmount, p);
        Transaction expectedTransaction = new Transaction(LocalDate.now().format(Transaction.DATE_TIME_FORMATTER),
                SALES_DESCRIPTION, SALES_CATEGORY, totalAmount, p, 0, false);
        assertEquals(expectedTransaction, transaction);
        modelManager.clearSalesList();
    }

    @Test
    public void checkoutAsTransaction_emptyCart_throwsNoItemToCheckoutException() throws AmountExceededException {
        setInventoryList();
        Person p = new PersonBuilder().build();
        modelManager.setCashier(p);
        double totalAmount = modelManager.getTotalAmount();
        assertThrows(NoItemToCheckoutException.class, () -> modelManager.checkoutAsTransaction(totalAmount, p));
        modelManager.clearSalesList();
    }

    @Test
    public void getCheckoutTransaction_successful() throws NoItemToCheckoutException, AmountExceededException {
        setInventoryList();
        Person p = new PersonBuilder().build();
        modelManager.setCashier(p);

        Item chips = new ItemBuilder()
                .withDescription(CHIPS.getDescription())
                .withQuantity(3)
                .withPrice(CHIPS.getPrice())
                .build();

        modelManager.addItem(chips);
        double totalAmount = modelManager.getTotalAmount();
        Transaction transaction = modelManager.checkoutAsTransaction(totalAmount, p);
        assertEquals(transaction, modelManager.getCheckoutTransaction());
        modelManager.clearSalesList();
    }

    @Test
    public void equals_successful() {
        // same values -> returns true
        modelManager = new ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        ModelManager modelManagerCopy = new ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        assertTrue(modelManager.equals(modelManagerCopy));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager()));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different sales list -> returns false

        modelManager.addItem(CHIPS);
        assertFalse(modelManager.equals(new ModelManager()));

        modelManager.clearSalesList();
    }

    @Test
    public void equalsSalesList_successful() {
        modelManager.clearSalesList();
        modelManager.addItem(CHIPS);
        modelManager.addItem(BURGER_AND_CHIPS);

        ArrayList<Item> list = new ArrayList<>();
        list.add(CHIPS);
        list.add(BURGER_AND_CHIPS);
        assertTrue(modelManager.equalsSalesList(list));
        modelManager.clearSalesList();
    }

    @Test
    public void equalsSalesList_differentSize_failure() {
        modelManager.addItem(CHIPS);

        ArrayList<Item> list = new ArrayList<>();
        list.add(CHIPS);
        list.add(BURGER_AND_CHIPS);
        assertFalse(modelManager.equalsSalesList(list));
        modelManager.clearSalesList();
    }

    @Test
    public void equalsSalesList_differentItems_failure() {
        modelManager.addItem(CHIPS);
        modelManager.addItem(STORYBOOK);

        ArrayList<Item> list = new ArrayList<>();
        list.add(CHIPS);
        list.add(BURGER_AND_CHIPS);
        assertFalse(modelManager.equalsSalesList(list));
        modelManager.clearSalesList();
    }

    @Test
    public void getCombination_successful() {
        char[] arr = "Goat".toCharArray();
        ArrayList<String> arr2 = modelManager.getCombination(arr, arr.length);

        ArrayList<String> expectedArr = new ArrayList<>();
        expectedArr.add("Goa");
        expectedArr.add("Goat");
        expectedArr.add("Gooa");
        expectedArr.add("Gooaat");
        expectedArr.add("Goa");
        expectedArr.add("Goaoat");
        expectedArr.add("Goat");
        assertEquals(expectedArr, arr2);
    }

    @Test
    public void getSubtotal_successful() {
        double subtotal = CHIPS.getQuantity() * CHIPS.getPrice();
        assertEquals(modelManager.getSubtotal(CHIPS), subtotal);
    }

    @Test
    public void onCashierMode_successful() {
        onCashierMode = true;
        assertTrue(modelManager.onCashierMode());
        onCashierMode = false;
    }

    @Test
    public void isValidAmountByDescription_returnFalse() throws NoSuchItemException, AmountExceededException {
        modelManager.clearSalesList();
        setInventoryList2();
        assertFalse(modelManager.isValidAmount(CHIPS.getDescription(), 99999999));
        modelManager.clearSalesList();
    }

    @Test
    public void isValidAmountByIndex_returnFalse() throws NoSuchItemException, AmountExceededException {
        modelManager.clearSalesList();
        setInventoryList2();
        modelManager.addItem(CHIPS);

        assertFalse(modelManager.isValidAmount(1, 99999999));
        modelManager.clearSalesList();
    }

    @Test
    public void isValidAmountByDescription_withItems_returnFalse()
            throws NoSuchItemException, AmountExceededException {
        modelManager.clearSalesList();
        setInventoryList2();
        modelManager.addItem(BURGER_AND_CHIPS);
        assertFalse(modelManager.isValidAmount(CHIPS.getDescription(), 1959999));
        assertThrows(AmountExceededException.class, () -> modelManager.isValidAmount(
                CHIPS.getDescription(), 195656));
        modelManager.clearSalesList();
    }
}
