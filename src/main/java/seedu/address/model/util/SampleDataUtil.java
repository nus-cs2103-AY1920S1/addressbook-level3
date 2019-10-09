package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.model.AddressBook;
import seedu.savenus.model.ReadOnlyAddressBook;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFood() {
        return new Food[] {
            new Food(new Name("Mala Xiang Guo"), new Price("10"), new Description("Spicy goodness from China"),
                    new Category("Chinese"), getTagSet("Spicy"), new OpeningHours("0900 1930"),
                    new Restrictions("Not Halal")),
            new Food(new Name("Ji Fan"), new Price("3.99"), new Description("Staple food for students"),
                    new Category("Chinese"), getTagSet("Chicken", "Rice"),
                    new OpeningHours("0800 1700"), new Restrictions("Not Halal")),
            new Food(new Name("Wagyu steak"), new Price("50"), new Description("Most expensive food in NUS"),
                    new Category("Western"), getTagSet("Japanese", "Expensive"),
                    new OpeningHours("1000 1700"), new Restrictions("Expensive")),
            new Food(new Name("Chicken noodle soup"), new Price("5.99"), new Description("Chicken and noodle and soup"),
                    new Category("Taiwanese"), getTagSet("Healthy", "Earthy"),
                    new OpeningHours("1000 1700"), new Restrictions("Not Halal")),
            new Food(new Name("Chessy spaghetti"), new Price("5.80"),
                    new Description("Italian noodle with epic cheese"), new Category("Italian"),
                    getTagSet("Cheese", "Pasta", "Healthy"), new OpeningHours("1100 1900"),
                    new Restrictions("Contains Dairy")),
            new Food(new Name("Vending Machine Sandwich"), new Price("4"),
                    new Description("Companion for boring ICube Lectures"), new Category("Vending Machine Food"),
                    getTagSet("colleagues"), new OpeningHours("0000 2359"),
                    new Restrictions(Restrictions.DEFAULT_VALUE))
        };
    }

    public static ReadOnlyMenu getSampleAddressBook() {
        Menu sampleAb = new Menu();
        for (Food sampleFood : getSampleFood()) {
            sampleAb.addFood(sampleFood);
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
