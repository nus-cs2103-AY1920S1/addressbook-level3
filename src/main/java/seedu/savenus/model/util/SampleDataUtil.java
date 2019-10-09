package seedu.savenus.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.model.Menu;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Menu} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFood() {
        return new Food[] {
            new Food(new Name("Mala Xiang Guo"), new Price("10"), new Description("Spicy goodness from China"),
                getTagSet("Spicy")),
            new Food(new Name("Chicken Rice"), new Price("3.99"), new Description("Staple food for students"),
                getTagSet("Chicken", "Rice")),
            new Food(new Name("Wagyu Steak"), new Price("50"), new Description("Most expensive food in NUS"),
                getTagSet("Japanese", "Expensive")),
            new Food(new Name("Chicken noodle soup"), new Price("5.99"), new Description("Chicken and noodle and soup"),
                getTagSet("Healthy", "Earthy")),
            new Food(new Name("Cheesy spaghetti"), new Price("5.80"), new Description("Italian noodle with epic cheese"),
                getTagSet("Cheese", "Pasta", "Healthy")),
            new Food(new Name("Vending Machine Sandwich"), new Price("4"), new Description("Companion for boring ICube Lectures"),
                getTagSet("Sandwich"))
        };
    }

    public static ReadOnlyMenu getSampleMenu() {
        Menu sampleMenu = new Menu();
        for (Food sampleFood : getSampleFood()) {
            sampleMenu.addFood(sampleFood);
        }
        return sampleMenu;
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
