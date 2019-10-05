package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Stub class representing the question of a Flashcard, is IMMUTABLE
 */
public class Question {

    private final String questionString;

    public Question(String questionString) {
        requireAllNonNull(questionString);
        this.questionString = questionString;
    }

    private String getQuestionString() {
        return questionString;
    }

    @Override
    public String toString() {
        return getQuestionString();
    }
}
