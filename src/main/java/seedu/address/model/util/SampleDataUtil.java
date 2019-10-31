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
            new Person(new Name("Amanda Lim"), new Phone("87438807"), new Email("amandalim@gmail.com"),
                new Gender("female"), new Address("180 Paya Lebar Road, #07-06, Singapore 409032"),
                getTagSet("freestyle", "medley"), new Photo("default.png")),
            new Person(new Name("Michael Phelps"), new Phone("3856289119"), new Email("mike@hotmail.com"),
                new Gender("Male"), new Address("3045 Hickory Heights Drive, Linthicum Heights, Maryland 21090"),
                getTagSet("freestyle", "olympics"), new Photo("default.png")),
            new Person(new Name("Tao Li"), new Phone("13092646433"), new Email("tao@yahoo.com"),
                new Gender("Female"), new Address("16号楼中单元401, 青岛市-城阳区, 山东省 266109"),
                getTagSet("backstroke", "butterfly"), new Photo("default.png")),
            new Person(new Name("Joseph Schooling"), new Phone("91031282"), new Email("jojo@outlook.com"),
                new Gender("Male"), new Address("Blk 216 Bedok North Street 1, #16-43, Singapore 460216"),
                getTagSet("butterfly", "olympics"), new Photo("default.png")),
            new Person(new Name("Sun Yang"), new Phone("12740154825"), new Email("sunyang@gmail.com"),
                new Gender("Male"), new Address("伟业路1号高新软件园9号楼9楼, 杭州 310000, 浙江"),
                getTagSet("freestyle"), new Photo("default.png")),
            new Person(new Name("Chad le Clos"), new Phone("0855590053"), new Email("chad@gmail.com"),
                new Gender("Male"), new Address("1769 Wattle St, Maclear, Eastern Cape 5481"),
                getTagSet("butterfly"), new Photo("default.png"))
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
