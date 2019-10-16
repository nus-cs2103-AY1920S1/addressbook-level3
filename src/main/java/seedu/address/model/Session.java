package seedu.address.model;

import java.util.Date;

import seedu.address.model.person.Person;

/**
 * Represents details relating to the session currently active in the incident management system.
 */
public class Session implements ReadOnlySession {
    private final Person person;
    private final Date loginTime;

    public Session(Person person) {
        this.person = person;
        loginTime = new Date();
    }

    @Override
    public Person getLoggedInPerson() {
        return person;
    }

    @Override
    public Date getLoginTime() {
        return loginTime;
    }
}
