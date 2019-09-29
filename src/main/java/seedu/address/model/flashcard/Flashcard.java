package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Stub class representing a Flashcard instance, is IMMUTABLE
 */
public class Flashcard {

    private final Answer answer;
    private final Question question;

    public Flashcard(Answer answer, Question question) {
        requireAllNonNull(answer, question);
        this.answer = answer;
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }
}
