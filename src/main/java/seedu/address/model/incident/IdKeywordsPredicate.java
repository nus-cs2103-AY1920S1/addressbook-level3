package seedu.address.model.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class IdKeywordsPredicate implements Predicate<Incident> {
    private final List<String> keywords = new ArrayList<>();

    public IdKeywordsPredicate(IncidentId idKeywords) {
        this.keywords.add(idKeywords.getId());
    }

    @Override
    public boolean test(Incident incident) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(incident.getIncidentId().getId(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IdKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((IdKeywordsPredicate) other).keywords)); // state check
    }

    public String getPredicate() {
        return keywords.toString();
    }
}
