package seedu.algobase.model.searchrule.plansearchrule;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.model.plan.Plan;

/**
 * Tests that a {@code Plan}'s {@code PlanDescription} includes all of the given keywords (ignoring cases).
 */
public class PlanDescriptionContainsKeywordsPredicate implements Predicate<Plan> {

    public static final PlanDescriptionContainsKeywordsPredicate DEFAULT_PLAN_DESCRIPTION_PREDICATE =
        new PlanDescriptionContainsKeywordsPredicate() {
            @Override
            public boolean test(Plan plan) {
                return true;
            }
        };

    private final List<Keyword> keywords;

    public PlanDescriptionContainsKeywordsPredicate(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    private PlanDescriptionContainsKeywordsPredicate() {
        this.keywords = null;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Plan plan) {
        assert keywords != null;
        return keywords.stream()
                .allMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(plan.getPlanDescription().value, keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        assert keywords != null;
        return other == this
                || (other instanceof PlanDescriptionContainsKeywordsPredicate
                && keywords.equals(((PlanDescriptionContainsKeywordsPredicate) other).getKeywords()));
    }
}
