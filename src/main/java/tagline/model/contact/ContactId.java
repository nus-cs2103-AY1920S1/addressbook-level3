// @@author yehezkiel01

package tagline.model.contact;

import tagline.model.contact.exceptions.InvalidIdException;

/**
 * Represents a Contact unique Id.
 */
public class ContactId {

    public static final String MESSAGE_CONSTRAINTS = "Contact ID must be a non-negative integer.";

    private static int contactIdDigits = 5;

    public final Long value;

    /**
     * Construct a {@code ContactId} from {@code String}.
     *
     * @param id
     */
    public ContactId(String id) {
        this(Long.valueOf(id));
    }

    /**
     * Constructs a contact Id from an integer.
     */
    public ContactId(long id) {
        if (id >= Math.pow(10, contactIdDigits)) {
            throw new InvalidIdException("Contact Id too large");
        }
        if (id < 0) {
            throw new InvalidIdException("Contact Id has to be a non-negative integer");
        }
        this.value = id;
    }

    /**
     * Returns true if {@code id} is valid.
     */
    public static boolean isValidId(String id) {
        Long value;

        try {
            value = Long.valueOf(id);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (0 <= value && value < Math.pow(10, contactIdDigits)) {
            return true;
        }

        return false;
    }

    /**
     * Increases the number of digits in ContactId.
     */
    static void incrementDigit() {
        contactIdDigits++;
    }

    static int getDigit() {
        return contactIdDigits;
    }

    @Override
    public String toString() {
        String idString = value.toString();
        while (idString.length() < contactIdDigits) {
            idString = "0" + idString;
        }
        return idString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ContactId)) {
            return false;
        }

        return value.equals(((ContactId) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
