package seedu.address.cashier.model;

import java.time.LocalDate;
import java.util.ArrayList;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class ModelManager implements Model {

    private static ArrayList<Item> salesList = new ArrayList<Item>();
    private InventoryList inventoryList;
    private StorageManager storage;
    private TransactionList transactionList;

    public ModelManager(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    public ModelManager(StorageManager storage) {
        this.storage = storage;
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
        try {
            this.transactionList = storage.getTransactionList();
        } catch (Exception e) {
            this.transactionList = new TransactionList();
        }
    }

    @Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    @Override
    public boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException {
        if (inventoryList.getOriginalItem(description).getQuantity() > quantity) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0 ; i < inventoryList.size(); i++) {
            try {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void addItem(Item item) {
        salesList.add(item);
    }

    @Override
    public Item findItemByIndex(int index) throws NoSuchIndexException {
        Item i = salesList.get(index - 1);
        return i;
    }

    @Override
    public void deleteItem(int index) {
        salesList.remove(index - 1);
    }


    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        inventoryList.set(i, editedItem);
    }

    @Override
    public void writeInInventoryFile() throws Exception{
        storage.writeFileToInventory(inventoryList);
    }

    public void updateInventoryList() throws Exception {
        for (int i = 0; i < salesList.size(); i++) {
            Item item = salesList.get(i);
            inventoryList.getOriginalItem(item).setQuantity(i + 1);
        }
    }

    public Transaction checkoutAsTransaction(double amount, Person person) {
        Transaction transaction = new Transaction(LocalDate.now().format(Transaction.myFormatter),
                "salesItems", "sales", amount, person, transactionList.size(), false);
        return transaction;
    }

}



