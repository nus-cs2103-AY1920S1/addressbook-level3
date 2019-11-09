package calofit.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import calofit.model.CalorieBudget;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.Name;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;
import calofit.model.tag.Tag;

/**
 * Contains utility methods for populating {@code DishDatabase} with sample data.
 */
public class SampleDataUtil {

    public static final int TYPICAL_BUDGET = 2200;

    public static Dish[] getSampleDishes() {
        return new Dish[] {
            new Dish(new Name("Spaghetti"),
                new Calorie(689),
                getTagSet("creamys")),
            new Dish(new Name("Mushroom Soup"),
                new Calorie(439),
                getTagSet("shitake", "expensive")),
            new Dish(new Name("Chicken Rice"),
                    new Calorie(894)),
            new Dish(new Name("Nasi Lemak"),
                new Calorie(742),
                getTagSet("value")),
            new Dish(new Name("Cheese Baked Rice"),
                    new Calorie(1132)),
            new Dish(new Name("Apple Pie"),
                    new Calorie(196))
        };
    }

    public static ReadOnlyDishDatabase getSampleDishDatabase() {
        DishDatabase sampleAb = new DishDatabase();
        for (Dish sampleDish : getSampleDishes()) {
            sampleAb.addDish(sampleDish);
        }
        return sampleAb;
    }

    public static ReadOnlyMealLog getNewMealLog() {
        return new MealLog();
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static CalorieBudget getSampleBudget() {
        return new CalorieBudget(Map.of(LocalDate.now(), TYPICAL_BUDGET));
    }
}
