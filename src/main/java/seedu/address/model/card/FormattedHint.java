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
        int zeroBasedIndex = hint.index.getZeroBased();

        if (zeroBasedIndex >= totalNumberOfCharacters) {
            throw new HintOutOfBoundsException(zeroBasedIndex, totalNumberOfCharacters);
        }

        arrayOfHintCharacters[zeroBasedIndex] = hint.letter;
    }

    /**
     * Formats all the characters into a {@code String} in the HangMan-style. Null characters are replaced
     * with an underscore, to represent that they are not provided yet.
     */
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
