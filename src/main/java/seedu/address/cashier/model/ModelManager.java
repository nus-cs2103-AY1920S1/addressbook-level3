package seedu.address.cashier.model;

import java.time.LocalDate;
import java.util.ArrayList;

import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.util.TransactionList;

public class ModelManager implements Model {

    public Person cashier = null;
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

    public boolean hasItemInInventory(String description) {
        for (int i = 0 ; i < inventoryList.size(); i++) {
            try {
                inventoryList.getOriginalItem(description);
                return true;
            } catch (NoSuchItemException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void addItem(Item item) {
        salesList.add(item);
    }

    public Item addItem(String description, int qty) throws NoSuchItemException {
        for (Item item : salesList) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                int originalQty = item.getQuantity();
                item.setQuantity(originalQty + qty);
                return item;
            }
        }
        Item i = inventoryList.getOriginalItem(description);
        i.setQuantity(qty);
        salesList.add(i);
        return i;
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

    public void setCashier(String name, seedu.address.person.model.Model personModel) throws NoSuchPersonException {
        try {
            this.cashier = personModel.getPersonByName(name);
        } catch (PersonNotFoundException e) {
            throw new NoSuchPersonException(CashierUi.NO_SUCH_PERSON);
        }
    }

    public Person getCashier() throws NoCashierFoundException {
        if (cashier == null) {
            throw new NoCashierFoundException(CashierUi.NO_CASHIER);
        }
        return this.cashier;
    }

    public Transaction checkoutAsTransaction(double amount, Person person) throws Exception {
        Transaction transaction = new Transaction(LocalDate.now().format(Transaction.myFormatter),
                "salesItems", "sales", amount, person, transactionList.size(), false);
        storage.appendToTransaction(transaction);
        return transaction;
    }

    public static double getTotalAmount() {
        double total = 0;
        for (Item i : salesList) {
            total += i.getPrice();
        }
        return total;
    }

    public ArrayList<Item> getSalesList() {
        return this.salesList;
    }

}



