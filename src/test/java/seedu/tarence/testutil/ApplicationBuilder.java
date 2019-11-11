package seedu.tarence.testutil;

import seedu.tarence.model.Application;
import seedu.tarence.model.person.Person;

/**
 * A utility class to help with building Applications.
 * Example usage: <br>
 *     {@code Applications ap = new ApplicationBuilder().withPerson("John", "Doe").build();}
 */
public class ApplicationBuilder {

    private Application application;

    public ApplicationBuilder() {
        application = new Application();
    }

    public ApplicationBuilder(Application application) {
        this.application = application;
    }

    /**
     * Adds a new {@code Person} to the {@code Application} that we are building.
     */
    public ApplicationBuilder withPerson(Person person) {
        application.addPerson(person);
        return this;
    }

    public Application build() {
        return application;
    }
}
