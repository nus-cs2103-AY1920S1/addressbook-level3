package seedu.sgm.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class holds all food lists of different categories. The categories are specified in
 */
public class FoodMap {

    private static final Map<FoodType, UniqueFoodList> foodRecms = new HashMap<>();

    /**
     * Initializes {@code foodListByType} to contain all food types and corresponding lists.
     */
    public FoodMap() {
        Stream.of(FoodType.values()).forEach(foodType -> foodRecms.put(foodType, new UniqueFoodList(foodType)));
    }

    /**
     * Replaces the contents of several food lists of specified food types with {@code foodLists}.
     */
    public void setFoodLists(UniqueFoodList... foodLists) {
        for (UniqueFoodList list : foodLists) {
            foodRecms.get(list.getFoodType()).setFoods(list);
        }
    }

    /**
     * Returns true if a food with the same name as {@code food} exists in the food recommendations.
     */
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodRecms.get(food.getFoodType()).contains(food);
    }

    /**
     * Adds a new food into recommendations. This food cannot be duplicate.
     */
    public void addFood(Food food) {
        requireAllNonNull(food);
        foodRecms.get(food.getFoodType()).add(food);
    }

    /**
     * Replaces the contents of this list with {@code foods}. {@code foods} must not contain duplicate foods.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);
        foodRecms.get(target.getFoodType()).setFood(target, editedFood);
    }

    /**
     * Removes the equivalent food from the list. The food must exist in the list.
     */
    public void removeFood(Food food) {
        foodRecms.get(food.getFoodType()).remove(food);
    }

    /**
     * Gets the food lists if their types are specified in {@code foodTypes}.
     *
     * @param foodTypes zero, one, or more {@code FoodType} to select food lists
     * @return a {@code List} of {@code UniqueFoodList} which correspond to the food types
     */
    public Map<FoodType, UniqueFoodList> getFoodRecms(FoodType... foodTypes) {
        if (foodTypes == null) {
            return foodRecms;
        }
        Map<FoodType, UniqueFoodList> requiredFoodMap = new HashMap<>();
        for (FoodType type : foodTypes) {
            assert foodRecms.get(type) != null;
            requiredFoodMap.put(type, foodRecms.get(type));
        }
        return requiredFoodMap;
    }
}
