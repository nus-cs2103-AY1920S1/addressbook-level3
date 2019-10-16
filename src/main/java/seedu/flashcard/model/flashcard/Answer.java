package seedu.flashcard.model.flashcard;

/**
 * The answer is just one of the choices in the flashcard.
 * Its message must exactly fit the correct choice.
 */
public class Answer extends Choice {

    public Answer(String choice) {
        super(choice);
    }

    public static final String MESSAGE_CONSTRAINTS = "Answer can take any values, and it should not be blank";

    /**
     * Returns true if a given string is a valid answer.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Answer
            && choice.equals(((Answer) other).choice));
    }
}
