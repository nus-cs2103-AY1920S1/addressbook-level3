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
            new Food(new Name("Chicken Rice"), new Price("5.80"), new Description("Chicken with fragrant rice"),
                getTagSet("friends")),
            new Food(new Name("Nasi Lemak"), new Price("4.50"), new Description("Coconut rice with fried chicken and cucumber"),
                getTagSet("colleagues", "friends")),
            new Food(new Name("Prawn Mee"), new Price("3.80"), new Description("Big prawns with yellow noodles"),
                getTagSet("neighbours")),
            new Food(new Name("Nasi Bryani"), new Price("5.20"), new Description("Fried chicken with rice"),
                getTagSet("family")),
            new Food(new Name("Lu Rou Fan"), new Price("4.50"), new Description("Braised pork with rice originating from Taiwan"),
                getTagSet("classmates")),
            new Food(new Name("Dry Mee Pok"), new Price("4.00"), new Description("Yellow flat noodles with lean pork and pork liver"),
                getTagSet("colleagues"))
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
