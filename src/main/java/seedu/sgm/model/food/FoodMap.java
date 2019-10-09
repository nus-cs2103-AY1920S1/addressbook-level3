package seedu.sgm.model.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class holds all food lists of different categories. The categories are specified in
 */
public class FoodMap {

    private static final Map<FoodType, UniqueFoodList> foodMap = new HashMap<>();

    /**
     * Initializes {@code foodListByType} to contain all food types and corresponding lists.
     */
    public FoodMap() {
        Stream.of(FoodType.values()).forEach(foodType -> foodMap.put(foodType, new UniqueFoodList(foodType)));
    }


    /**
     * Gets the food lists if their types are specified in {@code foodTypes}.
     *
     * @param foodTypes  zero, one, or more {@code FoodType} to select food lists
     * @return           a {@code List} of {@code UniqueFoodList} which correspond to the food types
     */
    public List<UniqueFoodList> getFoodListOf(FoodType... foodTypes) {
        if (foodTypes == null) {
            return new ArrayList<>(foodMap.values());
        }
        List<UniqueFoodList> lists = new ArrayList<>();
        for (FoodType type : foodTypes) {
            lists.add(foodMap.get(type));
        }
        return lists;
    }
}
