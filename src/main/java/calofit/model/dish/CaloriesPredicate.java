package calofit.model.dish;

import java.util.function.Predicate;

public class CaloriesPredicate implements Predicate<Dish> {

    private final int calories;

    public CaloriesPredicate (int calories) {
        this.calories = calories;
    }

    @Override
    public boolean test(Dish dish) {
        return dish.getCalories().getValue() <= this.calories;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CaloriesPredicate // instanceof handles nulls
            && calories == (((CaloriesPredicate) other).calories)); // state check
    }
}
