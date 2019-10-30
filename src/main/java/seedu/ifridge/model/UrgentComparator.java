package seedu.ifridge.model;

import seedu.ifridge.model.food.ShoppingItem;

import java.util.Comparator;

public class UrgentComparator implements Comparator<ShoppingItem> {
    public int compare(ShoppingItem si1, ShoppingItem si2) {
        if (si1.isUrgent() && !si2.isUrgent()) {
            return 0;
        } else if (!si1.isUrgent() && si2.isUrgent()) {
            return 1;
        } else {
            return si1.getName().toString().compareTo(si2.getName().toString());
        }
    }
}
