package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

import java.util.List;

import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicy;

public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        List<Policy> l = getTypicalPolicy();

        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Policy policy : getTypicalPolicy()) {
            ab.addPolicy(policy);
        }

        return ab;
    }
}
