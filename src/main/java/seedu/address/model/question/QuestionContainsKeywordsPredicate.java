package seedu.address.model.question;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Question}'s {@code QuestionBody} or {@code Answer} matches any of the keywords given.
 */
public class QuestionContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    public QuestionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Question question) {
        String body = question.getQuestionBody().body;
        String answer = question.getAnswer().answer;
        return keywords.stream().anyMatch(s -> body.toLowerCase().contains(s.toLowerCase())
                || answer.toLowerCase().contains(s.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuestionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((QuestionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
