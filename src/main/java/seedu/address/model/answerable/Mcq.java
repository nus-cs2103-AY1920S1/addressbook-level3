package seedu.address.model.answerable;

import seedu.address.model.category.Category;

import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Answerable in the Test Bank.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Mcq(Question question, Set<Answer> correctAnswerSet, Set<Answer> wrongAnswerSet,
               Difficulty difficulty, Set<Category> categories) {
        super(question, correctAnswerSet, wrongAnswerSet, difficulty, categories);
    }
}
