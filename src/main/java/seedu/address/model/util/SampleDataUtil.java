package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Price;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFood() {
        return new Food[] {
            new Food(new Name("Alex Yeoh"), new Price("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Food(new Name("Bernice Yu"), new Price("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Food(new Name("Charlotte Oliveiro"), new Price("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Food(new Name("David Li"), new Price("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Food(new Name("Irfan Ibrahim"), new Price("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Food(new Name("Roy Balakrishnan"), new Price("92624417"), new Email("royb@example.com"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
