package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;



/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Customer extends Person {

    public static final String MESSAGE_INVALID_ID = "Invalid customer ID.";

    private static int idCount = 0;
    private int id;

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
        id = ++idCount;
    }

    public Customer(int id, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.id = id;
    }

    public int getIdCount() {
        return idCount;
    }

    public int getId() {
        return id;
    }

    /**
     * Checks if {@code String id} can be parse into an integer and must be more than 0.
     *
     * @param id a unique number in string.
     */
    public static boolean isValidId(String id) {
        try {
            int tempInt = Integer.parseInt(id);
            return (tempInt > 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns a string representation of the customer, with identity fields visible to the user.
     *
     * @return string representation of customer
     */

    @Override
    public String toString() {
        StringBuilder customerBuilder = new StringBuilder();
        customerBuilder.append(" Customer stats: \n")
                .append(" id: ")
                .append(getId())
                .append(super.toString());
        return customerBuilder.toString();
    }
}
