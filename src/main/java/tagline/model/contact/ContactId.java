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
     * Constructs a contact Id from a String.
     * Warning: This constructor should only be used by storage.
     */
    public ContactId(String id) {
        this.id = Integer.valueOf(id);
    }

    /**
     * Constructs a contact Id from an integer.
     */
    ContactId(int id) {
        if (id >= Math.pow(10, contactIdDigits)) {
            throw new InvalidIdException("Id too large");
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

        int other;
        if (obj instanceof Integer) {
            other = (Integer) obj;
        } else if (obj instanceof ContactId) {
            other = ((ContactId) obj).id;
        } else {
            return false;
        }

        return id == other;
    }

    @Override
    public int hashCode() {
        return ((Integer) id).hashCode();
    }
}
