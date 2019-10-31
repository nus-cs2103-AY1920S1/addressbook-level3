package seedu.algobase.model.searchrule.plansearchrule;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.model.plan.Plan;

/**
 * Tests that a {@code Plan}'s {@code PlanName} matches any of the keywords given.
 */
public class PlanNameContainsKeywordsPredicate implements Predicate<Plan> {
    public static final PlanNameContainsKeywordsPredicate DEFAULT_PLAN_NAME_PREDICATE =
        new PlanNameContainsKeywordsPredicate() {
            @Override
            public boolean test(Plan plan) {
                return true;
            }
        };
    private final List<Keyword> keywords;

    public PlanNameContainsKeywordsPredicate(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    private PlanNameContainsKeywordsPredicate() {
        this.keywords = null;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Plan plan) {
        assert keywords != null;
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(plan.getPlanName().fullName, keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        assert keywords != null;
        return other == this
                || (other instanceof PlanNameContainsKeywordsPredicate
                && keywords.equals(((PlanNameContainsKeywordsPredicate) other).getKeywords()));
    }

}
