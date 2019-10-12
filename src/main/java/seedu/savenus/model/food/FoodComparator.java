package seedu.savenus.model.food;

import java.util.Comparator;
import java.util.List;

/**
 * A simple Comparator to arrange Food based on their fields.
 */
public class FoodComparator implements Comparator<Food> {

    private List<String> fieldList;

    public FoodComparator(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    @Override
    public int compare(Food a, Food b) {
        for (int i = 0; i < fieldList.size(); i++) {
            String field = fieldList.get(i);
            Field f1 = a.getField(field);
            Field f2 = b.getField(field);
            int comparisonFactor = f1.compareTo(f2);
            if (comparisonFactor != 0) {
                return comparisonFactor;
            }
        }
        return 0;
    }
}
