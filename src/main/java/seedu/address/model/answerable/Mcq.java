package seedu.address.model.answerable;

import seedu.address.model.tag.Tag;

import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Answerable in the category book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Mcq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Mcq(Question question, McqAnswer answer, Difficulty difficulty, Category category, Set<Tag> tags) {
        super(question, answer, difficulty, category, tags);
    }
}
