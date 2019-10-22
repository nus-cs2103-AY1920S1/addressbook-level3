package seedu.address.testutil;

import seedu.address.model.IncidentManager;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code IncidentManager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private IncidentManager incidentManager;

    public AddressBookBuilder() {
        incidentManager = new IncidentManager();
    }

    public AddressBookBuilder(IncidentManager incidentManager) {
        this.incidentManager = incidentManager;
    }

    /**
     * Adds a new {@code Person} to the {@code IncidentManager} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        incidentManager.addPerson(person);
        return this;
    }

    public IncidentManager build() {
        return incidentManager;
    }
}
