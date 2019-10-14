package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.groceryitem.Amount;
import seedu.address.model.groceryitem.Food;
import seedu.address.model.groceryitem.GroceryItem;
import seedu.address.model.groceryitem.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Alex Yeoh"), new Amount("300g"),
                getTagSet("friends")),
            new Food(new Name("Bernice Yu"), new Amount("300g"),
                getTagSet("colleagues", "friends")),
            new Food(new Name("Charlotte Oliveiro"), new Amount("300g"),
                getTagSet("neighbours")),
            new Food(new Name("David Li"), new Amount("300g"),
                getTagSet("family")),
            new Food(new Name("Irfan Ibrahim"), new Amount("300g"),
                getTagSet("classmates")),
            new Food(new Name("Roy Balakrishnan"), new Amount("300g"),
                getTagSet("colleagues"))
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
