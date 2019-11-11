package unrealunity.visit.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import unrealunity.visit.model.AddressBook;
import unrealunity.visit.model.ReadOnlyAddressBook;
import unrealunity.visit.model.person.Address;
import unrealunity.visit.model.person.Email;
import unrealunity.visit.model.person.Name;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.Phone;
import unrealunity.visit.model.person.VisitList;
import unrealunity.visit.model.person.VisitReport;
import unrealunity.visit.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final VisitList EMPTY_VISITATION_RECORD_1 = new VisitList(new ArrayList<VisitReport>());
    public static final VisitList EMPTY_VISITATION_RECORD_2 = new VisitList(new ArrayList<VisitReport>());
    public static final VisitList EMPTY_VISITATION_RECORD_3 = new VisitList(new ArrayList<VisitReport>());
    public static final VisitList EMPTY_VISITATION_RECORD_4 = new VisitList(new ArrayList<VisitReport>());
    public static final VisitList EMPTY_VISITATION_RECORD_5 = new VisitList(new ArrayList<VisitReport>());
    public static final VisitList EMPTY_VISITATION_RECORD_6 = new VisitList(new ArrayList<VisitReport>());
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), EMPTY_VISITATION_RECORD_1,
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_VISITATION_RECORD_2,
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_VISITATION_RECORD_3,
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_VISITATION_RECORD_4,
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_VISITATION_RECORD_5,
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), EMPTY_VISITATION_RECORD_6,
                getTagSet("colleagues"))
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
