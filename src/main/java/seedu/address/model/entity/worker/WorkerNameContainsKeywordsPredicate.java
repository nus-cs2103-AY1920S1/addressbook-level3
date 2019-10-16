package seedu.address.model.entity.worker;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class WorkerNameContainsKeywordsPredicate implements Predicate<Worker> {
    private final List<String> keywords;

    public WorkerNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Worker worker) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(worker.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkerNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((WorkerNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
