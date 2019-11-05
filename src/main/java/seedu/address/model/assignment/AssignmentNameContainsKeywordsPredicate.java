package seedu.address.model.assignment;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author weikiat97
/**
 * Tests that an {@code Assignment}'s {@code AssignmentName} matches any of the keywords given.
 */
public class AssignmentNameContainsKeywordsPredicate implements Predicate<Assignment> {

    private final List<String> keywords;

    public AssignmentNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Assignment assignment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(assignment.getAssignmentName().toString(),
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AssignmentNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
