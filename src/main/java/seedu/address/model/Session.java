package seedu.address.model;

import java.util.Date;

import seedu.address.model.person.Person;

//@@author madanalogy
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
        if (person == null) {
            return null;
        }
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getTags(),
                person.getUsername(), person.getPassword());
    }

    @Override
    public String getLoginTime() {
        return loginTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return otherSession.getLoggedInPerson().equals(getLoggedInPerson())
                && otherSession.getLoginTime().equals(getLoginTime());
    }
}
