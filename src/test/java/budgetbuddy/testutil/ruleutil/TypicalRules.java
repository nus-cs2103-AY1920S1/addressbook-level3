package budgetbuddy.testutil.ruleutil;

import java.util.List;

import budgetbuddy.model.rule.Rule;

/**
 * A utility class containing a list of {@code Rule} objects to be used in tests.
 */
public class TypicalRules {

    public static final Rule FOOD_DESC_FOOD = new RuleBuilder().withPredicate(TypicalPredicates.DESC_CONTAINS_FOOD)
            .withAction(TypicalActions.SET_CAT_FOOD).build();
    public static final Rule DAILY_DESC_REMOVE_DAILY = new RuleBuilder()
            .withPredicate(TypicalPredicates.DESC_CONTAINS_DAILY).withAction(TypicalActions.REMOVE_CAT_DAILY).build();
    public static final Rule DAILY_DESC_PREP_DAILY = new RuleBuilder()
            .withPredicate(TypicalPredicates.DESC_CONTAINS_DAILY).withAction(TypicalActions.PREP_DESC_DAILY).build();
    public static final Rule MOREEQUAL100_OUTAMT_HIDDEN = new RuleBuilder()
            .withPredicate(TypicalPredicates.OUTAMT_MOREEQUAL_100).withAction(TypicalActions.SET_DESC_HIDDEN).build();
    public static final Rule MOREEQUAL100_OUTAMT_EXP = new RuleBuilder()
            .withPredicate(TypicalPredicates.OUTAMT_MOREEQUAL_100).withAction(TypicalActions.APP_DESC_EXP).build();
    public static final Rule FOOD_DESC_OUT = new RuleBuilder().withPredicate(TypicalPredicates.DESC_CONTAINS_FOOD)
            .withAction(TypicalActions.SET_OUT).build();
    public static final Rule LESS10_INAMT_SWITCH = new RuleBuilder().withPredicate(TypicalPredicates.INAMT_LESSTHAN_10)
            .withAction(TypicalActions.SWITCH_DIRECT).build();

    public static final Rule SCRIPT_IN = new RuleBuilder().withPredicate(TypicalPredicates.TEST_SCRIPT)
            .withAction(TypicalActions.SET_IN).build();
    public static final Rule EQUAL4090_INAMT_SCRIPT = new RuleBuilder()
            .withPredicate(TypicalPredicates.INAMT_EQUAL_4090).withAction(TypicalActions.TEST_SCRIPT).build();
    public static final Rule SCRIPT_SCRIPT = new RuleBuilder().withPredicate(TypicalPredicates.TEST_SCRIPT)
            .withAction(TypicalActions.TEST_SCRIPT).build();

    public static final List<Rule> RULE_LIST =
            List.of(DAILY_DESC_PREP_DAILY, DAILY_DESC_REMOVE_DAILY, MOREEQUAL100_OUTAMT_HIDDEN, MOREEQUAL100_OUTAMT_EXP,
                    FOOD_DESC_OUT, SCRIPT_IN, EQUAL4090_INAMT_SCRIPT, SCRIPT_SCRIPT);

    private TypicalRules() {} // prevents instantiation
}
