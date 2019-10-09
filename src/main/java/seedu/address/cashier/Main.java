package seedu.address.cashier;

/*
import seedu.address.inventory.Item;

public class Main {

    private static HashMap<Item, Integer> itemList;

    public Main() {
        itemList = new HashMap<>();
    }

    public void addItem(Item itemToSell, int quantity) {
        if (!itemList.containsKey(itemToSell)) {
            if (itemToSell.getQuantity() >= quantity) {
                itemList.put(itemToSell, quantity);
            } else {
                // lion throw exception for invalid
            }
        } else {
            if (itemList.get(itemToSell) < quantity + itemList.get(itemToSell)) {
                // lion throw exception for invalid
            }
            itemList.put(itemToSell, quantity + itemList.get(itemToSell));
        }
    }

    // only delete 1 qty
    public void deleteItem(Item itemToDelete) {
        if (!itemList.containsKey(itemToDelete)) {
            // lion throw exception
        } else if(itemList.get(itemToDelete) >= 1) {
            itemList.computeIfPresent(itemToDelete, (k, v) -> v - 1);
        } else {
            // throw error about insufficient qty
        }
    }

    public void checkout(double Amount) {

    }

    public void markAsSold() {
        // add the total sales as 1 transaction
        Transaction transaction = new Transaction();
    }


}


*/









