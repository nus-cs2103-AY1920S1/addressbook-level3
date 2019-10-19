package seedu.savenus.model.sorter;

import java.util.Comparator;
import java.util.List;

import seedu.savenus.model.food.Field;
import seedu.savenus.model.food.Food;

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
        for (int i = 0; i < fieldList.size(); i = i + 2) {
            String field = fieldList.get(i);
            Field f1 = a.getField(field);
            Field f2 = b.getField(field);
            if (isDirectionAscending(fieldList.get(i + 1))) {
                int comparisonFactor = f1.compareTo(f2);
                if (comparisonFactor != 0) {
                    return comparisonFactor;
                }
            } else {
                int comparisonFactor = -1 * f1.compareTo(f2);
                if (comparisonFactor != 0) {
                    return comparisonFactor;
                }
            }
        }
        return 0;
    }

    public List<String> getFieldList() {
        return this.fieldList;
    }

    /**
     * Simply checks if the direction to be sorted is ascending or descending, for sorting purposes.
     * @param direction the direction to be sorted.
     * @return true if the direction is ascending. False if otherwise.
     */
    public boolean isDirectionAscending(String direction) {
        return direction.equals("ASC");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof FoodComparator) {
            return this.getFieldList().equals(((FoodComparator) obj).getFieldList());
        } else {
            return false;
        }
    }
}
