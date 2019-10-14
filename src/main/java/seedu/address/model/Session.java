package seedu.address.model;

import seedu.address.model.person.Person;

/**
 * Represents details relating to the session currently active in the incident management system.
 */
public class Session implements ReadOnlySession {
    private final Person person;

    public Session(Person person) {
        this.person = person;
    }

    @Override
    public Person getLoggedInPerson() {
        return person;
    }
}
