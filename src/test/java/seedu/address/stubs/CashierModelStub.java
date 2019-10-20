package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class CashierModelStub implements Model {

    @Override
    public void addItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Item addItem(String description, int qty) {
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
    public void writeInInventoryFile() throws Exception {
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
    public Transaction checkoutAsTransaction(double amount, Person person, seedu.address.transaction.model.Model transactionModel) throws Exception {
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
    public void readInUpdatedList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getStockLeft(String description) throws NoSuchItemException {
        throw new AssertionError("This method should not be called.");
    }
}

