package calofit.model.util;

import calofit.model.meal.Dish;
import calofit.model.meal.Name;
import calofit.model.AddressBook;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.tag.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
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

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
