package seedu.address.cashier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.Item;
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
    }

    @Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    @Override
    public boolean hasSufficientQuantity(Item i, int quantity) {
        try {
            if (InventoryList.getItem(i).getQuantity() > quantity) {
                return false;
                }
            else {
                return true;
            }
        } catch (Exception e) {
                return false;
        }
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        for (int i = 0 ; i < InventoryList.size(); i++) {
            try {
                if (InventoryList.getItemByIndex(i).equals(item)) {
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
    public void writeInInventoryFile() throws Exception{
        storage.writeFileToInventory(inventoryList);
    }

    @Override
    public void updateInventoryList() throws Exception {
        for (int i = 0; i < salesList.size(); i++) {
            Item item = salesList.get(i);
            inventoryList.getItem(item).setQuantity(i + 1);
        }
    }

    @Override
    public void Transaction checkoutAsTransaction() {
        Transaction transaction = new Transaction(LocalDateTime.now(), )
    }
}

