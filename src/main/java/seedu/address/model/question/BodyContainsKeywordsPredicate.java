package seedu.address.model.question;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Question}'s {@code QuestionBody} matches any of the keywords given.
 */
public class BodyContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    public BodyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        String body = question.getQuestionBody().body;
        return keywords.stream().anyMatch(s -> body.toLowerCase().contains(s.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BodyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BodyContainsKeywordsPredicate) other).keywords)); // state check
    }
}
