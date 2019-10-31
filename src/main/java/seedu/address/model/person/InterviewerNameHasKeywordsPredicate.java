package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Predicate that checks for given keywords in an Interviewer.
 */
public class InterviewerNameHasKeywordsPredicate implements Predicate<Interviewer> {

    private final List<String> keywords;

    public InterviewerNameHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Interviewer interviewer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(interviewer.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewerNameHasKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((InterviewerNameHasKeywordsPredicate) other).keywords)); // state check
    }
}
