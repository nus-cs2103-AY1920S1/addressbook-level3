package budgetbuddy.testutil.ruleutil;

import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.script.ScriptName;

/**
 * A utility class containing a list of {@code RuleAction} objects to be used in tests.
 */
public class TypicalActions {

    public static final RuleAction SET_CAT_FOOD = new ActionExpressionBuilder().withOperator("set_cat")
            .withValue("Food").build();
    public static final RuleAction REMOVE_CAT_DAILY = new ActionExpressionBuilder().withOperator("remove_cat")
            .withValue("daily").build();
    public static final RuleAction SET_DESC_HIDDEN = new ActionExpressionBuilder().withOperator("set_desc")
            .withValue("hidden").build();
    public static final RuleAction PREP_DESC_DAILY = new ActionExpressionBuilder().withOperator("prep_desc")
            .withValue("[Daily] ").build();
    public static final RuleAction APP_DESC_EXP = new ActionExpressionBuilder().withOperator("app_desc")
            .withValue(" - Expensive").build();
    public static final RuleAction SET_IN = new ActionExpressionBuilder().withOperator("set_in")
            .withValue("").build();
    public static final RuleAction SET_OUT = new ActionExpressionBuilder().withOperator("set_out")
            .withValue("").build();
    public static final RuleAction SWITCH_DIRECT = new ActionExpressionBuilder().withOperator("switch_direct")
            .withValue("").build();

    public static final RuleAction TEST_SCRIPT = new ActionScript(new ScriptName("test_act"));

    private TypicalActions() {} // prevents instantiation
}
