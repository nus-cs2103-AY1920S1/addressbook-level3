package seedu.address.cashier.model;

import java.util.ArrayList;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {

    InventoryList getInventoryList();

    TransactionList getTransactionList();

    boolean hasSufficientQuantityToAdd(String description, int quantity) throws NoSuchItemException;

    int getStockLeft(String description) throws NoSuchItemException;

    boolean hasItemInInventory(Item item);

    boolean hasItemInInventory(String description);

    void addItem(Item item);

    Item addItem(String description, int qty) throws NoSuchItemException;

    Item findItemByIndex(int index);

    void deleteItem(int index);

    void setItem(int i, Item editedItem) throws Exception;

    void setCashier(Person p);

    Person getCashier() throws NoCashierFoundException;

    ArrayList<Item> getSalesList();

    void clearSalesList();

    void resetCashier();

    Item editItem(int index, int qty) throws NoSuchIndexException, NoSuchItemException;

    double getSubtotal(Item i);

    boolean isSellable(String description) throws NoSuchItemException;

    ArrayList<String> getDescriptionByCategory(String category);

    ArrayList<String> getRecommendedItems(String description) throws NoSuchIndexException;

    Transaction checkoutAsTransaction(double amount, Person person) throws Exception;

    void updateInventoryList() throws Exception;

    double getTotalAmount() throws AmountExceededException;

    int findIndexByDescription(String description) throws NoSuchItemException;

    boolean hasSufficientQuantityToEdit(int index, int quantity) throws NoSuchItemException;

    void getUpdatedLists(InventoryList inventoryList, TransactionList transactionList);

    Transaction getCheckoutTransaction();

    boolean isValidAmount(String description, int qty) throws NoSuchItemException, AmountExceededException;

    boolean isValidAmount(int index, int qty) throws NoSuchItemException, AmountExceededException;

}
