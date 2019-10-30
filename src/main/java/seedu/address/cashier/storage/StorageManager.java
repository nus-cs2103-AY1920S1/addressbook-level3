package seedu.address.cashier.storage;

import java.util.ArrayList;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.model.Item;

import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Manages storage of Inventory List and Transaction List data in local storage.
 */
public class StorageManager implements Storage {

    private Logic inventoryLogic;
    private seedu.address.transaction.logic.Logic transactionLogic;

    public StorageManager(Logic inventoryLogic, seedu.address.transaction.logic.Logic transactionLogic) {
        this.inventoryLogic = inventoryLogic;
        this.transactionLogic = transactionLogic;
    }

    public InventoryList getInventoryList() throws Exception {
        ArrayList<Item> list = inventoryLogic.getInventoryListInArrayList();
        return new InventoryList(list);
    }

    /**
     * Writes the inventory list to the data file.
     * @param inventoryList the list to be written to file
     * @throws Exception if the input is invalid
     */
    public void writeToInventoryFile(InventoryList inventoryList) throws Exception {
        ArrayList<Item> list = inventoryList.getiArrayList();
        seedu.address.inventory.util.InventoryList inventoryList1 =
                new seedu.address.inventory.util.InventoryList(list);
        inventoryLogic.resetAndWriteIntoInventoryFile(inventoryList1);
    }

    public TransactionList getTransactionList() {
        return transactionLogic.getTransactionList();
    }

    /**
     * Appends the specified transaction to the data file.
     * @param transaction the transaction to be written to file
     * @throws Exception if the input is invalid
     */
    public void appendToTransaction(Transaction transaction) throws Exception {
        transactionLogic.appendToTransactionFile(transaction);
    }

}

