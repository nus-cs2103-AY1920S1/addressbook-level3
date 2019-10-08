package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Nric("S000001A"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new DateOfBirth("12.12.1998"), getPolicySet("teenage"), getTagSet("diabetic")),
            new Person(new Name("Bernice Yu"), new Nric("S000001B"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new DateOfBirth("11.11.1991"), getPolicySet("young adult", "midlife"),
                    getTagSet("medium priority", "low risk")),
            new Person(new Name("Charlotte Oliveiro"), new Nric("S000001C"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new DateOfBirth("10.9.2000"), getPolicySet("health"), getTagSet("low priority")),
            new Person(new Name("David Li"), new Nric("S000001D"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new DateOfBirth("4.6.1969"), getPolicySet("midlife", "retirement"),
                getTagSet("high priority")),
            new Person(new Name("Irfan Ibrahim"), new Nric("S000001F"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new DateOfBirth("09.04.2010"), getPolicySet("school"), getTagSet("smoker")),
            new Person(new Name("Roy Balakrishnan"), new Nric("S000001G"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new DateOfBirth("23.8.1999"), getPolicySet("car insurance"),
                    getTagSet("low health risk"))
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

    public static Set<Tag> getCriteriaSet(String... strings) {
        return getTagSet(strings);
    }

    public static Set<Policy> getPolicySet(String... strings) {
        return Arrays.stream(strings)
                .map(string -> new Policy(new PolicyName(string), new Description(string),
                        new Coverage(" days/10 months/11 years/12") , new Price("$500"),
                        new StartAge("50"), new EndAge("75"), getCriteriaSet("diabetic"),
                        getTagSet("sample")))
                .collect(Collectors.toSet());
    }

}
