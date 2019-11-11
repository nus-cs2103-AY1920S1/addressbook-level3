package seedu.address.person.model;

import seedu.address.person.model.person.Person;

/**
 * Acts as a facade that allows only some methods from the Model Manager.
 */
public interface CheckAndGetPersonByNameModel {

    /**
     * Retrieves the person with the same name from the Address Book.
     * @param name Specified name
     * @return Person of specified name
     */
    Person getPersonByName(String name);

    /**
     * Checks if the person is found in the Address Book.
     * @param person to find
     * @return true if the person can be found. Else, return false
     */
    boolean hasPerson(Person person);

}
