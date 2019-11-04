package seedu.ifridge.model;

import static seedu.ifridge.model.food.ShoppingItem.isCompletelyBought;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * Acts as a comparator that sorts the list.
 * Sorts the list such that urgent items are at the top and completely bought items are at the bottom of the list.
 */
public class ShoppingComparator implements Comparator<ShoppingItem> {
    private Model model;

    public ShoppingComparator(Model model) {
        this.model = model;
    }


    @Override
    public int compare(ShoppingItem si1, ShoppingItem si2) {
        ObservableList<GroceryItem> internalBoughtList = model.getBoughtList().getGroceryList();
        boolean isFirstItemCompletelyBought = isCompletelyBought(si1, internalBoughtList);
        boolean isSecondItemCompletelyBought = isCompletelyBought(si2, internalBoughtList);
        if (!isFirstItemCompletelyBought && isSecondItemCompletelyBought) {
            return -1;
        } else if (isFirstItemCompletelyBought && !isSecondItemCompletelyBought) {
            return 1;
        } else {
            if (si1.isUrgent() && !si2.isUrgent()) {
                return -1;
            } else if (!si1.isUrgent() && si2.isUrgent()) {
                return 1;
            } else {
                return si1.getName().toString().toLowerCase().compareTo(si2.toString().toLowerCase());
            }
        }
    }
}
