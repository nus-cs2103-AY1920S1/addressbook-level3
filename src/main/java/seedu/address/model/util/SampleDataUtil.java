package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.food.Amount;
import seedu.address.model.food.Food;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Alex Yeoh"), new Amount("300g")),
            new Food(new Name("Bernice Yu"), new Amount("300g")),
            new Food(new Name("Charlotte Oliveiro"), new Amount("300g")),
            new Food(new Name("David Li"), new Amount("300g")),
            new Food(new Name("Irfan Ibrahim"), new Amount("300g")),
            new Food(new Name("Roy Balakrishnan"), new Amount("300g"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Food sampleFood : getSampleFoods()) {
            sampleAb.addPerson((GroceryItem) sampleFood);
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
