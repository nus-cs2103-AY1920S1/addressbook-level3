package budgetbuddy.storage.rules;

import static budgetbuddy.storage.rules.JsonAdaptedActionTest.VALID_OPERATOR;
import static budgetbuddy.storage.rules.JsonAdaptedPredicateTest.VALID_ATTRIBUTE;
import static budgetbuddy.storage.rules.JsonAdaptedPredicateTest.VALID_SCRIPTNAME;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.testutil.ruleutil.TypicalRules;

public class JsonAdaptedRuleTest {
    private static final JsonAdaptedPredicate INVALID_PREDICATE =
            new JsonAdaptedPredicateExpression("", "", "");
    private static final JsonAdaptedAction INVALID_ACTION =
            new JsonAdaptedActionExpression("", "");

    private static final JsonAdaptedPredicate VALID_PREDICATE = new JsonAdaptedPredicateExpression(
            VALID_ATTRIBUTE, VALID_OPERATOR, JsonAdaptedPredicateTest.VALID_VALUE);
    private static final JsonAdaptedAction VALID_ACTION = new JsonAdaptedActionScript(VALID_SCRIPTNAME);

    @Test
    public void toModelType_validRuleDetails_returnsRule() throws Exception {
        JsonAdaptedRule rule = new JsonAdaptedRule(TypicalRules.DAILY_DESC_REMOVE_DAILY);
        assertEquals(TypicalRules.DAILY_DESC_REMOVE_DAILY, rule.toModelType());
    }

    @Test
    public void toModelType_invalidPredicate_throwsIllegalValueException() {
        JsonAdaptedRule rule = new JsonAdaptedRule(INVALID_PREDICATE, VALID_ACTION);
        String expectedMessage = Attribute.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }

    @Test
    public void toModelType_invalidAction_throwsIllegalValueException() {
        JsonAdaptedRule rule = new JsonAdaptedRule(VALID_PREDICATE, INVALID_ACTION);
        String expectedMessage = Operator.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }
}
