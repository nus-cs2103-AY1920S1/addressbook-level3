package seedu.address.person.model;

import seedu.address.person.model.person.Person;

/**
 * Acts as a facade that allows only some methods from the Model Manager.
 */
public interface GetPersonByNameOnlyModel {

    /**
     * Retrieves the person with the same name from the Address Book.
     * @param name Specified name
     * @return Person of specified name
     */
    Person getPersonByName(String name);

}
