package tagline.model.contact;

import tagline.model.contact.exceptions.InvalidIdException;

/**
 * Represents a Contact unique Id.
 */
public class ContactId {

    public static final String MESSAGE_CONSTRAINTS = "Id should be a positive integer.";

    private static int contactIdDigits = 5;

    private final int id;

    /**
     * Construct a contact Id from String.
     *
     * @param id
     */
    public ContactId(String id) {
        this(Integer.valueOf(id));
    }

    /**
     * Constructs a contact Id from an integer.
     */
    public ContactId(int id) {
        if (id >= Math.pow(10, contactIdDigits)) {
            throw new InvalidIdException("Id too large");
        }
        if (id < 0) {
            throw new InvalidIdException("Id has to be a positive number");
        }
        this.id = id;
    }

    /**
     * Returns true if {@code id} is valid.
     */
    public static boolean isValidId(String id) {
        int value;

        try {
            value = Integer.valueOf(id);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (0 <= value && value < (int) Math.pow(10, contactIdDigits)) {
            return true;
        }
        return false;
    }

    /**
     * Increases the number of digits in Id.
     */
    static void incrementDigit() {
        contactIdDigits++;
    }

    static int getDigit() {
        return contactIdDigits;
    }

    public Integer toInteger() {
        return id;
    }

    @Override
    public String toString() {
        String idString = ((Integer) id).toString();
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

        return id == ((ContactId) obj).id;
    }

    @Override
    public int hashCode() {
        return ((Integer) id).hashCode();
    }
}
