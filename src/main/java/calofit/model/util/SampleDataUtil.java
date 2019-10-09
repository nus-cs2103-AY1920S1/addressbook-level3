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
            new Dish(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Dish(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Dish(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Dish(new Name("David Li"),
                getTagSet("family")),
            new Dish(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Dish(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
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
