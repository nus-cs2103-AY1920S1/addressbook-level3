package sugarmummy.recmfood.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.FoodType;

/**
 * Tests that a {@code Food} belongs to any given {@code FoodType}.
 */
public class FoodTypeIsWantedPredicate implements Predicate<Food> {
    private final List<FoodType> foodTypes;

    public FoodTypeIsWantedPredicate(List<FoodType> foodTypes) {
        requireNonNull(foodTypes);
        this.foodTypes = foodTypes;
    }

    @Override
    public boolean test(Food food) {
        requireNonNull(food);
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
