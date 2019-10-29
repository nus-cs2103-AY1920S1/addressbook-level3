package seedu.address.cashier.storage;

import java.io.IOException;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.model.TransactionList;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Reads in the inventory list from the specified text file in Storage.
     * @return Inventory List read.
     */
    public InventoryList getInventoryList() throws Exception;

    Item readInInventoryFileLine(String line);

    void writeToInventoryFile(InventoryList inventoryList) throws IOException, NoSuchIndexException;

    TransactionList getTransactionList() throws Exception;

    Transaction readInTransactionFileLine(String line, seedu.address.person.model.Model personModel);

    void appendToTransaction(Transaction transaction) throws Exception;

}
