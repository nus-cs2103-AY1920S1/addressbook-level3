package seedu.sgm.model.food;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

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
