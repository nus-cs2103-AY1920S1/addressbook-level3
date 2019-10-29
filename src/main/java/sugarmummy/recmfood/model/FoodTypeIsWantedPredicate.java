package sugarmummy.recmfood.model;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Food} belongs to any given {@code FoodType}.
 */
public class FoodTypeIsWantedPredicate implements Predicate<Food> {
    private final List<FoodType> foodTypes;

    public FoodTypeIsWantedPredicate(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }

    @Override
    public boolean test(Food food) {
        return foodTypes.stream()
            .anyMatch(foodType -> food.getFoodType().equals(foodType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof FoodTypeIsWantedPredicate
            && foodTypes.equals(((FoodTypeIsWantedPredicate) other).foodTypes));
    }
}
