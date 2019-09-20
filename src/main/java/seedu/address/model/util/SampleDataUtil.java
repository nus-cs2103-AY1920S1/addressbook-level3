package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com")),
            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com")),
            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com")),
            new Person(new Name("David Li"), new Email("lidavid@example.com")),
            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com")),
            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
