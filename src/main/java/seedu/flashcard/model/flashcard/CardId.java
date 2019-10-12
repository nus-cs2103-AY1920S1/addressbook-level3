package seedu.flashcard.model.flashcard;

/**
 * The unique identity number for each flash card.
 * Guarantees: Each card's id number is unique.
 */
public class CardId {

    // The id number for the next flash card generated.
    private static int frontier = 0;
    private final int identityNumber;

    /**
     * Constructor of the class, automatically generate a unique identity number.
     */
    public CardId() {
        identityNumber = frontier;
        frontier++;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    /**
     * While finding a model, compare that the id number of this card matches the search string or not.
     * @param s The search parameter, target string.
     * @return true if the id number indeed contains the target information, false otherwise.
     */
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
        if (!(other instanceof CardId)) {
            return false;
        }
        return identityNumber == ((CardId) other).getIdentityNumber();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(identityNumber);
    }
}
