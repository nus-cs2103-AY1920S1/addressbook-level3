package seedu.system.model.participation;

import java.util.List;
import java.util.function.Predicate;

import seedu.system.commons.util.StringUtil;

/**
 * Tests that the {@code Name} of {@code Participation}'s  {@code Competition} or {@code Person} if it
 * matches any of the keywords given.
 */
public class PersonOrCompetitionNameContainsKeywordsPredicate implements Predicate<Participation> {
    private final List<String> keywords;

    public PersonOrCompetitionNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Participation participation) {
        return keywords.stream()
                .anyMatch(
                    keyword ->
                        StringUtil.containsWordIgnoreCase(participation.getCompetition().getName().toString(), keyword)
                    || StringUtil.containsWordIgnoreCase(participation.getPerson().getName().toString(), keyword)
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonOrCompetitionNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonOrCompetitionNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
