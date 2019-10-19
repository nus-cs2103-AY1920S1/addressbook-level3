package seedu.savenus.model.sorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a simple CustomSorter to sore the menu items every time a command is executed.
 */
public class CustomSorter {
    private FoodComparator foodComparator;

    /**
     * Creates a new simple CustomSorter.
     */
    public CustomSorter() {
        this.foodComparator = new FoodComparator(new ArrayList<String>());
    }

    /**
     * Sets the foodComparator based on a new list of fields.
     * @param fields the new list of fields.
     */
    public void setComparator(List<String> fields) {
        this.foodComparator = new FoodComparator(fields);
    }

    /**
     * Gets the foodComparator from the CustomSorter.
     * @return the foodComparator to sort the foods.
     */
    public FoodComparator getComparator() {
        return this.foodComparator;
    }
}
