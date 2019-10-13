package organice.model.util;

import organice.model.AddressBook;
import organice.model.ReadOnlyAddressBook;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Type;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Type("doctor"), new Nric("S1111111A"), new Name("Alex Yeoh"), new Phone("87438807")),
            new Person(new Type("doctor"), new Nric("T1333333P"), new Name("Bernice Yu"), new Phone("99272758")),
            new Person(new Type("doctor"), new Nric("G1234213L"), new Name("Charlotte Oliveiro"),
                    new Phone("93210283")),
            new Person(new Type("doctor"), new Nric("F5734625D"), new Name("David Li"), new Phone("91031282")),
            new Person(new Type("doctor"), new Nric("S6243536R"), new Name("Irfan Ibrahim"), new Phone("92492021")),
            new Person(new Type("doctor"), new Nric("T6746356G"), new Name("Roy Balakrishnan"), new Phone("92624417"))
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
