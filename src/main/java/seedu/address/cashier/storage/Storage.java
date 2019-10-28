package seedu.address.cashier.storage;

import seedu.address.cashier.util.InventoryList;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Reads in the inventory list from the specified text file in Storage.
     * @return Inventory List read.
     */
    InventoryList getInventoryList() throws Exception;

    void writeToInventoryFile(InventoryList inventoryList) throws Exception;

    TransactionList getTransactionList() throws Exception;

    void appendToTransaction(Transaction transaction) throws Exception;

    //InventoryList getInventoryList(seedu.address.inventory.util.InventoryList inventoryList);

}
