package seedu.address.testutil;

import seedu.address.model.Planner;
import seedu.address.model.contact.Contact;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Planner ab = new PlannerBuilder().withPerson("John", "Doe").build();}
 */
public class PlannerBuilder {

    private Planner planner;

    public PlannerBuilder() {
        planner = new Planner();
    }

    public PlannerBuilder(Planner planner) {
        this.planner = planner;
    }

    /**
     * Adds a new {@code Contact} to the {@code Planner} that we are building.
     */
    public PlannerBuilder withContact(Contact contact) {
        planner.addContact(contact);
        return this;
    }

    public Planner build() {
        return planner;
    }
}
