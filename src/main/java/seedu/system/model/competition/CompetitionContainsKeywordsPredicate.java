package seedu.system.model.competition;

import java.util.List;
import java.util.function.Predicate;

import seedu.system.commons.util.StringUtil;

/**
 * Tests that a {@code Competition}'s {@code Name} or {@code Date}s matches any of the keywords given.
 */
public class CompetitionContainsKeywordsPredicate implements Predicate<Competition> {
    private final List<String> keywords;

    public CompetitionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Competition competition) {
        return keywords.stream()
                .anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(competition.getName().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(competition.getStartDate().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(competition.getEndDate().toString(), keyword)
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompetitionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CompetitionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
