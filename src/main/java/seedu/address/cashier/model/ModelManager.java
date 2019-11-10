package seedu.address.cashier.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_TOTAL_AMOUNT_EXCEEDED;
import static seedu.address.cashier.ui.CashierMessages.NO_ITEM_TO_CHECKOUT;
import static seedu.address.inventory.model.Item.DECIMAL_FORMAT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.AmountExceededException;
import seedu.address.cashier.model.exception.NoItemToCheckoutException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Represents the in-memory model of the sales list data.
 */
public class ModelManager implements Model {

    protected static boolean onCashierMode = false;

    private static final String SALES_DESCRIPTION = "Items sold";
    private static final String SALES_CATEGORY = "Sales";
    private static ArrayList<Item> salesList = new ArrayList<Item>();
    private Person cashier = null;
    private InventoryList inventoryList;
    private TransactionList transactionList;
    private Transaction checkoutTransaction;
    private final Logger logger = LogsCenter.getLogger(getClass());


    /**
     * Initializes a ModelManager with the given inventory list and transaction list.
     */
    public ModelManager(InventoryList inventoryList, TransactionList transactionList) {
        this.inventoryList = inventoryList;
        this.transactionList = transactionList;
    }

    public ModelManager() {
        this.inventoryList = new InventoryList();
        this.transactionList = new TransactionList();
    }

    /**
     * Returns a view of the {@code InventoryList}.
     * @return
     */
    @Override
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    /**
     * Returns a view of the {@code TransactionList}.
     */
    @Override
    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    /**
     * Updates the inventory and transaction list.
     *
     * @param inventoryList of the current inventory
     * @param transactionList of the current transaction
     */
    @Override
    public void getUpdatedLists(InventoryList inventoryList, TransactionList transactionList) {
        this.inventoryList = inventoryList;
        this.transactionList = transactionList;
    }

    /**
     * Returns true if the quantity keyed in is less than or equals to the quantity available in inventory.
     * Else, return false.
     *
     * @param description of the item to check
     * @param quantity of the item to check
     * @return true if sufficient quantity in inventory
     * @throws NoSuchItemException if there is no such item in the inventory
     */
    @Override
    public boolean hasSufficientQuantityToAdd(String description, int quantity) throws NoSuchItemException {
        assert description != null : "Description cannot be null.";

        Item originalItem = inventoryList.getOriginalItem(description);
        for (Item i : salesList) {
            if (originalItem.isSameItem(i)) {
                logger.info("Item to be added already exist in table.");
                int initialSalesQty = i.getQuantity();
                return (originalItem.getQuantity() >= (initialSalesQty + quantity));
            }
        }
        if (originalItem.getQuantity() >= quantity) {
            logger.info("New item from inventory added to table.");
            return true;
        }
        return false;
    }

    /**
     * Returns true if the quantity keyed in is less than or equals to the quantity available in inventory.
     * Else, return false.
     *
     * @param index of the item to edit
     * @param quantity of the item to check
     * @return true if sufficient quantity in inventory
     * @throws NoSuchItemException if there is no such item in the inventory
     */
    @Override
    public boolean hasSufficientQuantityToEdit(int index, int quantity) throws NoSuchItemException {
        assert index > 0 : "Index must be a positive integer.";
        assert quantity >= 0 : "Quantity must be positive integer.";

        Item salesItem = salesList.get(index - 1);
        Item i = inventoryList.getOriginalItem(salesItem);
        return i.getQuantity() >= quantity;
    }

    /**
     * Returns the quantity of stock left for a specific item.
     * @param description of the item
     * @return an integer representing the quantity of stock left
     * @throws NoSuchItemException if the item do not exist
     */
    @Override
    public int getStockLeft(String description) throws NoSuchItemException {
        assert description != null : "Description of item cannot be null.";
        return inventoryList.getOriginalItem(description).getQuantity();
    }

    /**
     * Returns true if an item with the same attributes as {@code item} exists in the Inventory List.
     */
    @Override
    public boolean hasItemInInventory(Item item) {
        assert item != null : "Item to find cannot be null.";
        try {
            for (int i = 0; i < inventoryList.size(); i++) {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            }
        } catch (NoSuchIndexException e) {
            return false;
        }
        return false;
    }

    /**
     * Returns true if an item with the same description as {@code item} exists in the Inventory List.
     * Else, return false.
     * @param description of the item to check
     * @return true if item exist in inventory
     */
    @Override
    public boolean hasItemInInventory(String description) {
        assert description != null : "Description cannot be null.";
        return inventoryList.hasItem(description);
    }

    @Override
    public void addItem(Item item) {
        assert item != null : "Item added cannot be null.";
        salesList.add(item);
        onCashierMode = true;
    }

    /**
     * Adds the item into the Sales List.
     * @param description of the item to be added
     * @param qty of the item to be added
     * @return the item added
     * @throws NoSuchItemException if the description is invalid
     */
    @Override
    public Item addItem(String description, int qty) throws NoSuchItemException {
        requireNonNull(description);
        onCashierMode = true;
        for (Item item : salesList) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                int originalQty = item.getQuantity();
                int newQty = originalQty + qty;
                salesList.remove(item);
                addItem(description, newQty);
                return item;
            }
        }
        Item i = inventoryList.getOriginalItem(description);
        Item copy = new Item(i.getDescription(), i.getCategory(), qty, i.getCost(), i.getPrice(),
                Integer.valueOf(i.getId()));
        copy.setQuantity(qty);
        salesList.add(copy);
        return i;
    }

    @Override
    public Item findItemByIndex(int index) {
        Item i = salesList.get(index - 1);
        return i;
    }

    @Override
    public int findIndexByDescription(String description) throws NoSuchItemException {
        requireNonNull(description);
        for (int i = 0; i < salesList.size(); i++) {
            Item item = salesList.get(i);
            if (item.getDescription().equalsIgnoreCase(description)) {
                return i;
            }
        }
        throw new NoSuchItemException(CashierMessages.NO_SUCH_DESCRIPTION_CASHIER);
    }

    @Override
    public void deleteItem(int index) {
        salesList.remove(index - 1);
        onCashierMode = salesList.size() == 0 ? false : true;
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        salesList.set(i, editedItem);
    }

    /**
     * Updates the quantity in the inventory list according to the quantity sold in Sales List.
     * @throws Exception if an item is invalid
     */
    @Override
    public void updateInventoryList() throws Exception {
        for (int i = 0; i < salesList.size(); i++) {
            Item item = salesList.get(i);
            int originalQty = inventoryList.getOriginalItem(item).getQuantity();
            inventoryList.getOriginalItem(item).setQuantity(originalQty - item.getQuantity());
        }
    }

    /**
     * Sets the specified {@code Person} as the cashier.
     * @param p the person to be set as cashier
     */
    @Override
    public void setCashier(Person p) {
        requireNonNull(p);
        this.cashier = p;
    }

    /**
     * Returns the cashier-in-charge.
     * @return the cashier-in-charge
     * @throws NoCashierFoundException if no cashier has been set
     *
     */
    @Override
    public Person getCashier() throws NoCashierFoundException {
        if (cashier == null) {
            throw new NoCashierFoundException(CashierMessages.NO_CASHIER);
        }
        return this.cashier;
    }

    /**
     * Returns the total amount of all the items in the Sales List.
     * @return the total amount of all the items in the Sales List
     */
    @Override
    public double getTotalAmount() throws AmountExceededException {
        double total = 0.00;
        for (Item i : salesList) {
            total += (i.getPrice() * i.getQuantity());
        }
        if (total > 999999.99) {
            throw new AmountExceededException(MESSAGE_TOTAL_AMOUNT_EXCEEDED);
        }
        return total;
    }

    /**
     * Returns the Sales List.
     * @return the Sales List
     */
    @Override
    public ArrayList<Item> getSalesList() {
        return this.salesList;
    }

    /**
     * Clears all the items in the Sales List.
     */
    @Override
    public void clearSalesList() {
        this.salesList = new ArrayList<Item>();
        onCashierMode = false;
    }

    /**
     * Resets the cashier to null.
     */
    @Override
    public void resetCashier() {
        this.cashier = null;
    }

    /**
     * Edits the item to update the quantity to be sold.
     * @param index of the item to be updated
     * @param qty of the item to be updated
     * @return the item edited
     */
    @Override
    public Item editItem(int index, int qty) throws NoSuchItemException {
        String description = salesList.get(index - 1).getDescription();
        salesList.remove(index - 1);

        Item i = inventoryList.getOriginalItem(description);
        Item copyItem = new Item(i.getDescription(), i.getCategory(), qty, i.getCost(), i.getPrice(),
                Integer.valueOf(i.getId()));
        salesList.add(index - 1, copyItem);
        return salesList.get(index - 1);
    }

    /**
     * Returns the subtotal of the item according to the quantity and the price.
     * @param i the item to be calculated
     * @return the subtotal of the item
     */
    @Override
    public double getSubtotal(Item i) {
        return Double.parseDouble(DECIMAL_FORMAT.format(i.getPrice() * i.getQuantity()));
    }

    /**
     * Returns true if the item with the specified description is available for sale. Else, returns false.
     * @param description of the item
     * @return true if available for sale
     * @throws NoSuchItemException if not for sale
     */
    @Override
    public boolean isSellable(String description) throws NoSuchItemException {
        requireNonNull(description);
        Item i = inventoryList.getOriginalItem(description);
        return i.isSellable();
    }

    /**
     * Returns a list of sales items according to their category.
     * @param category of the items
     * @return a list of sales items according to their category
     */
    @Override
    public ArrayList<String> getDescriptionByCategory(String category) {
        requireNonNull(category);
        return inventoryList.getAllSalesDescriptionByCategory(category);
    }


    /**
     * Returns a list of recommended items based on the initial input description.
     * @param description of the item
     * @return a list of recommended items
     * @throws NoSuchIndexException if inventory list is invalid
     */
    @Override
    public ArrayList<String> getRecommendedItems(String description) throws NoSuchIndexException {
        requireNonNull(description);
        ArrayList<String> recommendedItems = new ArrayList<>();
        for (int i = 0; i < inventoryList.size(); i++) {
            Item item = inventoryList.getItemByIndex(i);
            String itemDescription = item.getDescription().toLowerCase();
            description = description.toLowerCase();

            // if the item is not for sale, skip
            if (!item.isSellable()) {
                continue;
            }

            // if both description start with same letters, add
            if (itemDescription.startsWith(description)) {
                recommendedItems.add(item.getDescription()); // return exact description
                continue;
            }

            // if length of input is greater than 3 and either description contains the other
            if (description.length() >= 3
                    && ((itemDescription.contains(description))
                    || description.contains(itemDescription))) {
                recommendedItems.add(item.getDescription()); // return exact description
                continue;
            }

            /* if length of input is greater than 3 and each subset of the input contains the description in the
            ** inventory or vice versa
            */
            if (description.length() >= 3) {
                char[] arr = description.toCharArray();
                ArrayList<String> combinations = getCombination(arr, arr.length);
                for (int j = 0; j < combinations.size(); j++) {
                    if (combinations.get(j).contains(itemDescription)
                            || itemDescription.contains(combinations.get(j))) {
                        recommendedItems.add(item.getDescription());
                        continue;
                    }
                }
            }
        }
        ArrayList<String> newList = recommendedItems.stream()
                .distinct()
                .collect(Collectors
                        .toCollection(ArrayList::new));
        return newList;
    }

    /**
     * Returns all subsets of the given character array that are of at least length 3.
     *
     * @param arr the character array
     * @param n the length of the character array
     * @return all subsets of the given character array that are longer than 3 characters
     */
    public ArrayList<String> getCombination(char[] arr, int n) {
        assert arr != null : "Array to get combination from cannot be null.";
        ArrayList<String> result = new ArrayList<>();
        for (int start = 1; start <= n; start++) {
            String word = "";
            for (int i = 0; i <= n - start; i++) {
                //  Adds characters from current starting point to current ending point
                int j = i + start - 1;
                for (int k = i; k <= j; k++) {
                    word += String.valueOf(arr[k]);
                }
                result.add(word);
            }
        }
        return result.stream()
                .filter(str -> str.length() >= 3)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }

    /**
     * Creates a new {@code Transaction} and append it to the data file.
     * Adds the transaction to the transaction model.
     *
     * @param amount to paid by customer
     * @param person cashier who is in-charge
     * @return the new transaction made from the sales
     * @throws Exception if the user input is invalid
     */
    @Override
    public Transaction checkoutAsTransaction(double amount, Person person) throws NoItemToCheckoutException {
        assert person != null : "Person cannot be null.";
        if (salesList.size() <= 0) {
            throw new NoItemToCheckoutException(NO_ITEM_TO_CHECKOUT);
        }
        Transaction transaction = new Transaction(LocalDate.now().format(Transaction.DATE_TIME_FORMATTER),
                SALES_DESCRIPTION, SALES_CATEGORY, amount, person, this.transactionList.size(), false);
        checkoutTransaction = transaction;
        onCashierMode = false;
        return transaction;
    }

    /**
     * Returns the checkout transaction.
     *
     * @return the checkout transaction
     *
     */
    @Override
    public Transaction getCheckoutTransaction() {
        return checkoutTransaction;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return inventoryList.equals(other.getInventoryList())
                && transactionList.equals(other.getTransactionList())
                && this.equalsSalesList(other.getSalesList());
    }

    /**
     * Compares the sales list with another list of items to check if all the elements of both list are equal.
     *
     * @param list the other list to compare against
     * @return true if both list are exactly the same. Else, return false
     */
    public boolean equalsSalesList(ArrayList<Item> list) {
        boolean result = true;
        if (list.size() != this.getSalesList().size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            // compares every element of sales list
            if (result && this.getSalesList().get(i).equals(list.get(i))) {
                continue;
            } else {
                result = false;
                return false;
            }
        }
        return result;
    }

    @Override
    public boolean isValidAmount(String description, int qty) throws NoSuchItemException, AmountExceededException {
        assert qty > 0 : "Quantity must be a positive integer.";
        requireNonNull(description);

        double price = inventoryList.getOriginalItem(description).getPrice();
        double itemPrice = price * qty;
        if (itemPrice > 999999.99) {
            return false;
        } else {
            double total = 0;
            for (Item i : salesList) {
                total += (i.getPrice() * i.getQuantity());
            }
            if ((total + itemPrice) > 999999.99) {
                throw new AmountExceededException(MESSAGE_TOTAL_AMOUNT_EXCEEDED);
            }
            return true;
        }
    }

    @Override
    public boolean isValidAmount(int index, int qty) throws NoSuchItemException, AmountExceededException {
        assert qty > 0 : "Quantity must be a positive integer.";
        assert index > 0 : "Index must be a positive integer.";

        String description = salesList.get(index - 1).getDescription();
        return isValidAmount(description, qty);
    }

    public static boolean onCashierMode() {
        return onCashierMode;
    }

}



