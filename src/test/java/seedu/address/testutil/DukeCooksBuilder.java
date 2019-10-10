package seedu.address.testutil;

import seedu.address.model.DukeCooks;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building DukeCooks objects.
 * Example usage: <br>
 *     {@code DukeCooks dc = new DukeCooksBuilder().withPerson("John", "Doe").build();}
 */
public class DukeCooksBuilder {

    private DukeCooks dukeCooks;

    public DukeCooksBuilder() {
        dukeCooks = new DukeCooks();
    }

    public DukeCooksBuilder(DukeCooks dukeCooks) {
        this.dukeCooks = dukeCooks;
    }

    /**
     * Adds a new {@code Person} to the {@code DukeCooks} that we are building.
     */
    public DukeCooksBuilder withPerson(Person person) {
        dukeCooks.addPerson(person);
        return this;
    }

    public DukeCooks build() {
        return dukeCooks;
    }
}
