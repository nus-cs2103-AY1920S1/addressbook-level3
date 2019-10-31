package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Predicate that checks for given keywords in an Interviewee.
 */
public class IntervieweeNameHasKeywordsPredicate implements Predicate<Interviewee> {

    private final List<String> keywords;

    public IntervieweeNameHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Interviewee interviewee) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(interviewee.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IntervieweeNameHasKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IntervieweeNameHasKeywordsPredicate) other).keywords)); // state check
    }
}
