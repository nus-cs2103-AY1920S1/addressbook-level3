package seedu.sgm.model.food;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;

/**
 * Represents a food map that can be filtered by keywords.
 */
public class FilteredFoodMap extends FoodMap {

    private static final Predicate ALWAYS_TRUE = t -> true;
    private Map<FoodType, FilteredList<Food>> filteredListMap;

    public FilteredFoodMap(FoodMap source) {
        this(source, null);
    }

    public FilteredFoodMap(FoodMap source, Predicate<Food> predicate) {
        for (UniqueFoodList list : source.getFoodRecms().values()) {
            filteredListMap.put(list.getFoodType(), new FilteredList<Food>(list.asUnmodifiableObservableList()));
        }
    }

    public void setPredicate(Predicate<Food> predicate) {
        requireNonNull(predicate);
        for (FilteredList<Food> list : filteredListMap.values()) {
            list.setPredicate(predicate);
        }
    }

}
