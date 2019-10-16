package seedu.address.model.quiz.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Question}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;
    private List<String> instruction;
    private boolean allowTypo;

    public NameContainsKeywordsPredicate(List<String> keywords, boolean allowTypo) {
        this.keywords = keywords;
        this.allowTypo = allowTypo;
    }

    public NameContainsKeywordsPredicate(List<String> instruction, List<String> keywords, boolean allowTypo) {
        this.instruction = instruction;
        this.keywords = keywords;
        this.allowTypo = allowTypo;
    }

    @Override
    public boolean test(Question question) {
        if (instruction == null) {
            return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsQuizWordMatch(question.getName().fullName, keyword.trim(), allowTypo)
                    || StringUtil.containsQuizWordMatch(question.getAnswer().value, keyword.trim(), allowTypo)
                    || StringUtil.containsQuizWordMatch(question.getCategory().value, keyword.trim(), allowTypo)
                    || StringUtil.containsQuizWordMatch(question.getType().value, keyword.trim(), allowTypo)
                    || StringUtil.containsTagQuizIgnoreCase(question.getTags(), keyword.trim(), allowTypo));
        } else {
            return getInstructionKeywordResult(question);
        }
    }

    private boolean getInstructionKeywordResult(Question question) {
        boolean isFound = false;

        for (String subInstructionKeyword : instruction) {
            String field = subInstructionKeyword.trim().toLowerCase();

            if (field.equals("question")) {
                isFound = isFound || keywords.stream().anyMatch(
                    keyword -> StringUtil.containsQuizWordMatch(question.getName().fullName, keyword, allowTypo));
            } else if (field.equals("answer")) {
                isFound = isFound || keywords.stream().anyMatch(
                    keyword -> StringUtil.containsQuizWordMatch(question.getAnswer().value, keyword, allowTypo));
            } else if (field.equals("category")) {
                isFound = isFound || keywords.stream().anyMatch(
                    keyword -> StringUtil.containsQuizWordMatch(question.getCategory().value, keyword, allowTypo));
            } else if (field.equals("type")) {
                isFound = isFound || keywords.stream().anyMatch(
                    keyword -> StringUtil.containsQuizWordMatch(question.getType().value, keyword, allowTypo));
            } else if (field.equals("tag")) {
                isFound = isFound || keywords.stream().anyMatch(
                    keyword -> StringUtil.containsTagQuizIgnoreCase(question.getTags(), keyword, allowTypo));
            }
        }

        return isFound;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
