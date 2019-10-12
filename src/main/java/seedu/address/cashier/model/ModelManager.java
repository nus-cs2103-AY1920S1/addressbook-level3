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
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class ModelManager implements Model {

    private static final String SALES_DESCRIPTION = "Items sold";
    private static final String SALES_CATEGORY = "Sales";
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
            //System.out.println(inventoryList);
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
            //System.out.println("not loaded");;
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
        Item originalItem = inventoryList.getOriginalItem(description);
        for (Item i : salesList) {
            if (originalItem.isSameItem(i)) {
                int initialSalesQty = i.getQuantity();
                return (originalItem.getQuantity() >= (initialSalesQty + quantity));
            }
        }
        if (originalItem.getQuantity() >= quantity) {
            return true;
        }
        return false;
    }

    public int getStockLeft(String description) throws NoSuchItemException {
        return inventoryList.getOriginalItem(description).getQuantity();
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        //for (int i = 0 ; i < inventoryList.size(); i++) {
        try {
            for (int i = 0; i < inventoryList.size(); i++) {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            }
        } catch (Exception e) {
                return false;
            }
        return false;
    }

    public void updateRecentInventory() throws Exception {
        this.inventoryList = storage.getInventoryList();
    }

    public boolean hasItemInInventory(String description) throws NoSuchIndexException {
        return inventoryList.hasItem(description);
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
        updateInventoryList();
        storage.writeFileToInventory(inventoryList);
    }

    public void updateInventoryList() throws Exception {
        for (int i = 0; i < salesList.size(); i++) {
            System.out.println("size " + salesList.size());
            Item item = salesList.get(i);
            System.out.println("1item" + item);
            int originalQty = inventoryList.getOriginalItem(item).getQuantity();
            System.out.println("original item " + inventoryList.getOriginalItem(item));
            inventoryList.getOriginalItem(item).setQuantity(originalQty - item.getQuantity());
            System.out.println("2item" + item);
            System.out.println("original item2 " + inventoryList.getOriginalItem(item));
            System.out.println("sales qty " + salesList.get(i).getQuantity());
            System.out.println("original qty " + originalQty);
            System.out.println(originalQty - item.getQuantity());
        }
    }

    public void setCashier(Person p) {
        this.cashier = p;
    }

    public Person getCashier() throws NoCashierFoundException {
        if (cashier == null) {
            throw new NoCashierFoundException(CashierUi.NO_CASHIER);
        }
        return this.cashier;
    }

    public Transaction checkoutAsTransaction(double amount, Person person) throws Exception {
        Transaction transaction = new Transaction(LocalDate.now().format(Transaction.DATE_TIME_FORMATTER),
                SALES_DESCRIPTION, SALES_CATEGORY, amount, person, transactionList.size(), false);
        storage.appendToTransaction(transaction);
        return transaction;
    }

    public static double getTotalAmount() {
        double total = 0;
        for (Item i : salesList) {
            total += (i.getPrice() * i.getQuantity());
        }
        return total;
    }

    public ArrayList<Item> getSalesList() {
        return this.salesList;
    }

}



