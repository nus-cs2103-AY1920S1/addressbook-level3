package seedu.address.Flashcard;

/**
 * The unique identity number for each flash card.
 * Guarantees: Each card's id number is unique.
 */
public class CardID {

    private final int identityNumber;
    // The id number for the next flash card generated.
    private static int frontier = 0;

    /**
     * Constructor of the class, automatically generate a unique identity number.
     */
    public CardID() {
        identityNumber = frontier;
        frontier++;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public boolean contains(String s) {
        String idAsString = Integer.toString(identityNumber);
        return idAsString.contains(s);
    }

    @Override
    public String toString() {
        return Integer.toString(identityNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CardID)) {
            return false;
        }
        return identityNumber == ((CardID) other).getIdentityNumber();
    }

    @Override
    public int hashCode() {
        return identityNumber;
    }
}
