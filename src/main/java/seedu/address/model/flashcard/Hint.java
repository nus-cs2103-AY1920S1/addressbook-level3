package seedu.address.model.flashcard;

/**
 * Stub class representing a Hint for a flashcard, is IMMUTABLE
 */
public class Hint {
    private String hintString;

    public Hint(String hintString) {
        this.hintString = hintString;
    }

    private String getHintString() {
        return hintString;
    }

    @Override
    public String toString() {
        return hintString;
    }
}
