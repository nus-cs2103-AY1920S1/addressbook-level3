package seedu.address.model.util;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh")),
            new Person(new Name("Bernice Yu")),
            new Person(new Name("Charlotte Oliveiro")),
            new Person(new Name("David Li")),
            new Person(new Name("Irfan Ibrahim")),
            new Person(new Name("Roy Balakrishnan")),
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Person samplePerson : getSamplePersons()) {
            sampleDc.addPerson(samplePerson);
        }
        return sampleDc;
    }
}
