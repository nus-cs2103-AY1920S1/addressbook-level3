package budgetbuddy.testutil.ruleutil;

import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.model.script.ScriptName;

/**
 * A utility class containing a list of {@code RulePredicate} objects to be used in tests.
 */
public class TypicalPredicates {

    public static final RulePredicate DESC_CONTAINS_FOOD = new PredicateExpressionBuilder().withAttribute("desc")
            .withOperator("contains").withValue("food").build();
    public static final RulePredicate DESC_CONTAINS_DAILY = new PredicateExpressionBuilder().withAttribute("desc")
            .withOperator("contains").withValue("daily").build();
    public static final RulePredicate INAMT_LESSTHAN_10 = new PredicateExpressionBuilder().withAttribute("inamt")
            .withOperator("<").withValue("10").build();
    public static final RulePredicate OUTAMT_MOREEQUAL_100 = new PredicateExpressionBuilder()
            .withAttribute("outamt").withOperator(">=").withValue("100").build();
    public static final RulePredicate INAMT_EQUAL_4090 = new PredicateExpressionBuilder()
            .withAttribute("inamt").withOperator("=").withValue("4090").build();

    public static final RulePredicate TEST_SCRIPT = new PredicateScript(new ScriptName("test_pred"));

    private TypicalPredicates() {} // prevents instantiation
}
