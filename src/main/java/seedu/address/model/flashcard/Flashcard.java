package seedu.address.model.flashcard;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Stub class representing a Flashcard instance, is IMMUTABLE
 */
public class Flashcard {

    private final Answer answer;
    private final Question question;
    private final Hint hint;

    public Flashcard(Answer answer, Question question, Hint hint) {
        requireAllNonNull(answer, question);
        this.answer = answer;
        this.question = question;
        this.hint = hint;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Hint getHint() {
        return hint;
    }

}
