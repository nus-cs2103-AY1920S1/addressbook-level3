package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReferenceId;
import seedu.address.model.common.Tag;
import seedu.address.model.person.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.person.parameters.Phone;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SamplePersonDataUtil {

    /**
     * Parses the {@refId} as a patient's {@code ReferenceId}.
     */
    private static ReferenceId patientRefId(String refId) {
        try {
            return PersonReferenceId.parsePatientReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    /**
     * Parses the {@refId} as a staff's {@code ReferenceId}.
     */
    private static ReferenceId staffRefId(String refId) {
        try {
            return PersonReferenceId.parseStaffReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(patientRefId("001A"), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(patientRefId("002B"), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(patientRefId("003C"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(patientRefId("004D"), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(patientRefId("005E"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(patientRefId("006F"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Person[] getSampleStaffMember() {
        return new Person[] {
                new Person(staffRefId("S001A"), new Name("DR Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
                new Person(staffRefId("S002B"), new Name("DR Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
                new Person(staffRefId("S003C"), new Name("DR Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
                new Person(staffRefId("S004D"), new Name("DR David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
                new Person(staffRefId("S005E"), new Name("DR Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
                new Person(staffRefId("S006F"), new Name("DR Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
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

    public static ReadOnlyAddressBook getSampleStaffAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSampleStaffMember()) {
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
