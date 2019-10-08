package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Content;
import seedu.address.model.person.Person;
import seedu.address.model.person.Title;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Title("Alex Yeoh"), new Content("Blk 30 Geylang Street 29, #06-40")),
            new Person(new Title("Bernice Yu"), new Content("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Person(new Title("Charlotte Oliveiro"), new Content("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Person(new Title("David Li"), new Content("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Person(new Title("Irfan Ibrahim"), new Content("Blk 47 Tampines Street 20, #17-35")),
            new Person(new Title("Roy Balakrishnan"), new Content("Blk 45 Aljunied Street 85, #11-31"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addNote(samplePerson);
        }
        return sampleAb;
    }
}
