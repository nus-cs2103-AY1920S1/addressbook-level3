package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Customer extends Person {

    private int id;
    static int IDCount = 0;

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        id = IDCount;
        IDCount++;
    }

    public int getIDCount(){ return IDCount; }

    public int getID(){ return id; }

}
