package seedu.address.model;

import java.util.Date;

import seedu.address.model.person.Person;

/**
 * Unmodifiable view of a login session
 */
public interface ReadOnlySession {
    Person getLoggedInPerson();

    Date getLoginTime();
}
