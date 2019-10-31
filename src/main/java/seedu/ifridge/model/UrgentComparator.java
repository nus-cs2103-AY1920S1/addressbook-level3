package seedu.ifridge.model;

import java.util.Comparator;

import seedu.ifridge.model.food.ShoppingItem;

/**
 * Acts as a comparator to sort the shopping lists.
 */
public class UrgentComparator implements Comparator<ShoppingItem> {
    /**
     * Ensures that urgent items are shown at the top of the shopping list.
     * Sorted by urgent first, then alphabetical order.
     * @param si1 first shopping item
     * @param si2 second shopping item
     * @return -ve integer if si1 is to be placed before, +ve if si2 is to be placed before, 0 otherwise
     */
    public int compare(ShoppingItem si1, ShoppingItem si2) {
        if (si1.isUrgent() && !si2.isUrgent()) {
            return -1;
        } else if (!si1.isUrgent() && si2.isUrgent()) {
            return 1;
        } else {
            return si1.getName().toString().toLowerCase().compareTo(si2.getName().toString().toLowerCase());
        }
    }
}
