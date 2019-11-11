package seedu.sugarmummy.model.recmf.predicates;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodType;

/**
 * Tests that a {@code Food} belongs to any given {@code FoodType}.
 */
public class FoodTypeIsWantedPredicate implements Predicate<Food> {
    private final Set<FoodType> foodTypes;

    public FoodTypeIsWantedPredicate(Set<FoodType> foodTypes) {
        requireNonNull(foodTypes);
        this.foodTypes = foodTypes;
    }

    @Override
    public boolean test(Food food) {
        requireNonNull(food);

        //Shows the full food list if no flag is specified.
        if (foodTypes.size() == 0) {
            return true;
        }
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
