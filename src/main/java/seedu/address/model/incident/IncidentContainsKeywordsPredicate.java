package seedu.address.model.incident;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class IncidentContainsKeywordsPredicate implements Predicate<Incident> {
    private final List<String> keywords;

    public IncidentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Incident incident) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(incident.getDesc().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncidentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IncidentContainsKeywordsPredicate) other).keywords)); // state check
    }

}
