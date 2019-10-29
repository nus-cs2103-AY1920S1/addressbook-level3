package seedu.address.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified card.
 */
public class HintOutOfBoundsException extends RuntimeException {

    public HintOutOfBoundsException(int index, int hintSize) {
        super(String.format("Hint out of bounds: Index %d of hintSize %d!", index, hintSize));
    }

}
