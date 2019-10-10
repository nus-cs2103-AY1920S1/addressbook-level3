package seedu.address.model.answerable;

import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Represents a Answerable in the category book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Saq extends Answerable {

    /**
     * Every field must be present and not null.
     */
    public Saq(Question question, SaqAnswer answers, Difficulty difficulty, Category category, Set<Tag> tags) {
        super(question, answers, difficulty, category, tags);
    }
}
