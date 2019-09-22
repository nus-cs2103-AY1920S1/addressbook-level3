package seedu.tarence.model.util;

import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;

/**
 * Contains utility methods for populating {@code Application} with sample data.
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

    public static ReadOnlyApplication getSampleApplication() {
        Application sampleAb = new Application();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
