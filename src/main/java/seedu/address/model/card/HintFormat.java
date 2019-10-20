package seedu.address.model.card;

public class HintFormat {
    private int totalNumberOfCharacters;
    private char[] arrayOfHintCharacters;

    HintFormat(int totalNumberOfCharacters) {
        this.totalNumberOfCharacters = totalNumberOfCharacters;
        arrayOfHintCharacters = new char[totalNumberOfCharacters];
    }

    public int getHintFormatSize() {
        return totalNumberOfCharacters;
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
