package seedu.address.model.incident;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class DescriptionKeywordsPredicate implements Predicate<Incident> {
    private final List<String> keywords;

    public DescriptionKeywordsPredicate(List<String> descriptionKeywords) {
        assert descriptionKeywords != null : "No description keywords found.";
        this.keywords = descriptionKeywords;
    }

    @Override
    public boolean test(Incident incident) {
        assert incident.getDesc() != null : "Incident should have Description description";
        assert incident.getDesc().toString() != "" : "Description should not be empty.";
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(incident.getDesc().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionKeywordsPredicate) other).keywords)); // state check
    }

    public String getPredicate() {
        return keywords.toString();
    }
}
