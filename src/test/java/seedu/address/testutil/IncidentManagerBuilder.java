package seedu.address.testutil;

import seedu.address.model.IncidentManager;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building IncidentManager objects.
 * Example usage: <br>
 *     {@code IncidentManager ab = new IncidentManagerBuilder().withPerson("John", "Doe").build();}
 */
public class IncidentManagerBuilder {

    private IncidentManager incidentManager;

    public IncidentManagerBuilder() {
        incidentManager = new IncidentManager();
    }

    public IncidentManagerBuilder(IncidentManager incidentManager) {
        this.incidentManager = incidentManager;
    }

    /**
     * Adds a new {@code Person} to the {@code IncidentManager} that we are building.
     */
    public IncidentManagerBuilder withPerson(Person person) {
        incidentManager.addPerson(person);
        return this;
    }

    public IncidentManager build() {
        return incidentManager;
    }
}
