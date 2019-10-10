package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Category;
import seedu.address.model.person.Difficulty;
import seedu.address.model.person.Question;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Question("Alex Yeoh"), new Difficulty("87438807"), new Category("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Question("Bernice Yu"), new Difficulty("99272758"),
                    new Category("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Question("Charlotte Oliveiro"), new Difficulty("93210283"),
                    new Category("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours")),
            new Person(new Question("David Li"), new Difficulty("91031282"), new
                    Category("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family")),
            new Person(new Question("Irfan Ibrahim"), new Difficulty("92492021"),
                    new Category("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates")),
            new Person(new Question("Roy Balakrishnan"), new Difficulty("92624417"),
                    new Category("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
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
