package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.medical.MedicalHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Person samplePerson : getSamplePersons()) {
            sampleDc.addPerson(samplePerson);
        }
        return sampleDc;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<MedicalHistory> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalHistory::new)
                .collect(Collectors.toSet());
    }

}
