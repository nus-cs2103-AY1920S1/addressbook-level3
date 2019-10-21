package seedu.address.model.card;

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

    void updateHintArray(Hint hint) {
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
