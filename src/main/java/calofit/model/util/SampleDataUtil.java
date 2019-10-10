package calofit.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.Name;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.tag.Tag;

/**
 * Contains utility methods for populating {@code DishDatabase} with sample data.
 */
public class SampleDataUtil {
    public static Dish[] getSampleDishes() {
        return new Dish[] {
            new Dish(new Name("Spaghetti"),
                getTagSet("creamys")),
            new Dish(new Name("Mushroom Soup"),
                getTagSet("shitake", "expensive")),
            new Dish(new Name("Chicken Rice")),
            new Dish(new Name("Nasi Lemak"),
                getTagSet("value")),
            new Dish(new Name("Cheese Baked Rice")),
            new Dish(new Name("Apple Pie"))
        };
    }

    public static ReadOnlyDishDatabase getSampleDishDatabase() {
        DishDatabase sampleAb = new DishDatabase();
        for (Dish sampleDish : getSampleDishes()) {
            sampleAb.addDish(sampleDish);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
