package seedu.address.model.card;

import seedu.address.model.card.exceptions.HintOutOfBoundsException;

/**
 * Class that represents a Hint that is formatted in a HangMan-style, ready to be output.
 */
public class FormattedHint {
    private int totalNumberOfCharacters;
    private char[] arrayOfHintCharacters;

    FormattedHint(int totalNumberOfCharacters) {
        this.totalNumberOfCharacters = totalNumberOfCharacters;
        arrayOfHintCharacters = new char[totalNumberOfCharacters];
    }

    /**
     * Updates the {@code arrayOfHintCharacters} with the new {@code Hint's} character at the specified index.
     * @throws HintOutOfBoundsException if input Hint's index is out of range of {@code arrayOfHintCharacters}.
     */
    void updateHintArray(Hint hint) throws HintOutOfBoundsException {
        if (hint.index.getZeroBased() >= totalNumberOfCharacters) {
            throw new HintOutOfBoundsException(hint.index.getZeroBased(), totalNumberOfCharacters);
        }

        arrayOfHintCharacters[hint.index.getZeroBased()] = hint.letter;
    }

    @Override
    public String toString() {
        StringBuilder outputFormat = new StringBuilder(totalNumberOfCharacters);
        for (int i = 0; i < arrayOfHintCharacters.length; i++) {
            if ((int) arrayOfHintCharacters[i] == 0) { // checking for ASCII null character
                outputFormat.append('_');
            } else {
                outputFormat.append(arrayOfHintCharacters[i]);
            }
        }
        return outputFormat.toString();
    }
}
