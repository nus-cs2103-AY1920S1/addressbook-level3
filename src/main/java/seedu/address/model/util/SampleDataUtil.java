package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Performance;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static Event[] getSampleEvents() {
        return new Event[] {
            new Event("freestyle 50m"), new Event("backstroke 100m"),
            new Event("breaststroke 100m"), new Event("butterfly 100m"),
            new Event("medley 200m"), new Event("freestyle relay 4 x 100m")
        };
    }

    public static ReadOnlyPerformance getSamplePerformance() {
        Performance samplePerformance = new Performance();
        for (Event sampleEvent : getSampleEvents()) {
            samplePerformance.addEvent(sampleEvent);
        }
        return samplePerformance;
    }

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"), new Gender(
                "Male"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new Photo("default.png")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Gender("Female"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), new Photo("default.png")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Gender("Female"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new Photo("default.png")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Gender("Male"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new Photo("default.png")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Gender("Male"), new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new Photo("default.png")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Gender("Others"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Photo("default.png"))
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
