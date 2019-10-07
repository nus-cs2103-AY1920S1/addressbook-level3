package calofit.model.util;

import calofit.model.meal.Meal;
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
    public static Meal[] getSampleMeals() {
        return new Meal[] {
            new Meal(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Meal(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Meal(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Meal(new Name("David Li"),
                getTagSet("family")),
            new Meal(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Meal(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Meal sampleMeal : getSampleMeals()) {
            sampleAb.addMeal(sampleMeal);
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
