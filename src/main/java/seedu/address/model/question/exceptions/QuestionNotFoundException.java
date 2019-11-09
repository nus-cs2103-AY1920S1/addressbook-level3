package seedu.address.model.question.exceptions;

import seedu.address.commons.core.Messages;

/**
 * Signals that the operation will result in duplicate Questions (Questions are considered duplicates if they have
 * all the same type of fields).
 */
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super(Messages.MESSAGE_QUESTION_NOT_FOUND);
    }
}
