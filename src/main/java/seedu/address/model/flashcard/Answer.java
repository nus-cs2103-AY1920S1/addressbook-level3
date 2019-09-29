package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Stub class representing the correct answer of a Flashcard, is IMMUTABLE
 */
public class Answer {

    private final String answerString;

    public Answer(String answerString) {
        requireAllNonNull(answerString);
        this.answerString = answerString;
    }

    public String getAnswerString() {
        return answerString;
    }
}
