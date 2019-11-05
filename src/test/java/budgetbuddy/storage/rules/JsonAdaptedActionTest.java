package budgetbuddy.storage.rules;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.testutil.ruleutil.TypicalActions;

public class JsonAdaptedActionTest {
    public static final String INVALID_OPERATOR = "clear";
    public static final String INVALID_VALUE = "@test";

    public static final String VALID_OPERATOR = "app_desc";
    public static final String VALID_VALUE = "food";
    public static final String VALID_SCRIPTNAME = "test";

    @Test
    public void toModelType_validActionExpression_returnsAction() throws Exception {
        JsonAdaptedAction action = new JsonAdaptedActionExpression(
                (ActionExpression) TypicalActions.APP_DESC_EXP);
        assertEquals(TypicalActions.APP_DESC_EXP, action.toModelType());
    }

    @Test
    public void toModelType_validActionScript_returnsAction() throws Exception {
        JsonAdaptedAction action = new JsonAdaptedActionScript(
                (ActionScript) TypicalActions.TEST_SCRIPT);
        assertEquals(TypicalActions.TEST_SCRIPT, action.toModelType());
    }

    @Test
    public void toModelType_invalidOperator_throwsIllegalValueException() {
        JsonAdaptedAction action = new JsonAdaptedActionExpression(INVALID_OPERATOR, VALID_VALUE);
        String expectedMessage = Operator.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, action::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedAction action = new JsonAdaptedActionExpression(VALID_OPERATOR, INVALID_VALUE);
        String expectedMessage = Value.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, action::toModelType);
    }
}
