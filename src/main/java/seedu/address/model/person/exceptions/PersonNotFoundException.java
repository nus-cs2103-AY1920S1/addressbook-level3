package seedu.address.model.person.exceptions;

import seedu.address.model.person.Name;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Name name) {
        super("Unable to find person: " + name.toString());
    }


    public PersonNotFoundException(){

    }

}
