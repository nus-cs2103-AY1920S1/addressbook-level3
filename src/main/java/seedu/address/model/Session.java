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

    // Used for testing
    public Session(Person person, Date loginTime) {
        this.person = person;
        this.loginTime = loginTime;
    }

    @Override
    public Person getLoggedInPerson() {
        return person;
    }

    @Override
    public Date getLoginTime() {
        return loginTime;
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
