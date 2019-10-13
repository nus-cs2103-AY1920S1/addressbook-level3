package seedu.flashcard.model.flashcard;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.AppUtil.checkArgument;

/**
 * The definition of a word in the flashcard.
 * TODO: Can be replaced by the options in the MCQ flashcard
 */
public class Definition {

    public static final String MESSAGE_CONSTRAINTS = "Definition can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final Speech speech;
    public final String definition;

    public Definition(String definition, Speech speech) {
        requireNonNull(definition);
        checkArgument(isValidDefinition(definition));
        this.definition = definition;
        this.speech = speech;
    }

    /**
     * Returns true if a given string is a valid definition.
     */
    public static boolean isValidDefinition(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return speech.toString() + " " + definition;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Definition)
                && definition.equals(((Definition) other).definition)
                && speech.equals(((Definition) other).speech);
    }

    @Override
    public int hashCode() {
        return hash(definition, speech);
    }
}
