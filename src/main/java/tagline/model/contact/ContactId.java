package tagline.model.contact;

import tagline.model.contact.exceptions.InvalidIdException;

/**
 * Represent a Contact unique Id.
 */
public class ContactId {

    public static final String MESSAGE_CONSTRAINTS = "id should be a positive integer up to 5 digit";

    private static int contactIdDigits = 5;

    private final int id;

    /**
     * Construct an Id from String.
     * Warning: This constructor should only be used by storage.
     * @param id
     */
    public ContactId(String id) {
        this.id = Integer.valueOf(id);
    }

    /**
     * Construct Id from integer.
     */
    ContactId(int id) {
        if (id >= Math.pow(10, contactIdDigits)) {
            throw new InvalidIdException("Id too large");
        }
        this.id = id;
    }

    /**
     * Returns true if id is valid.
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
     * Increase the number of digit in Id.
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
