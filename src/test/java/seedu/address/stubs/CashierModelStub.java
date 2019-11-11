package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Represents a Cashier tab's Model stub.
 */
public class CashierModelStub implements Model {

    @Override
    public void addItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Item addItem(String description, int qty) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Item findItemByIndex(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteItem(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCashier(Person p) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getCashier() throws NoCashierFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Item> getSalesList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearSalesList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetCashier() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Item editItem(int index, int qty) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double getSubtotal(Item i) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSellable(String description) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<String> getDescriptionByCategory(String category) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<String> getRecommendedItems(String description) throws NoSuchIndexException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Transaction checkoutAsTransaction(double amount, Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateInventoryList() throws Exception {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public double getTotalAmount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int findIndexByDescription(String description) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSufficientQuantityToEdit(int index, int quantity) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void getUpdatedLists(InventoryList inventoryList, TransactionList transactionList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Transaction getCheckoutTransaction() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isValidAmount(String description, int qty) throws NoSuchItemException, AmountExceededException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isValidAmount(int index, int qty) throws NoSuchItemException, AmountExceededException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasItemInInventory(String description) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public InventoryList getInventoryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public TransactionList getTransactionList() {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public boolean hasSufficientQuantityToAdd(String description, int quantity) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getStockLeft(String description) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }
}

