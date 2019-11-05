package budgetbuddy.storage.rules;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.testutil.ruleutil.TypicalPredicates;

public class JsonAdaptedPredicateTest {
    public static final String INVALID_ATTRIBUTE = "name";
    public static final String INVALID_OPERATOR = "more than";
    public static final String INVALID_VALUE = "t@est";

    public static final String VALID_ATTRIBUTE = "desc";
    public static final String VALID_OPERATOR = "contains";
    public static final String VALID_VALUE = "daily";
    public static final String VALID_SCRIPTNAME = "test";

    @Test
    public void toModelType_validPredicateExpression_returnsPredicate() throws Exception {
        JsonAdaptedPredicate predicate = new JsonAdaptedPredicateExpression(
                (PredicateExpression) TypicalPredicates.INAMT_EQUAL_4090);
        assertEquals(TypicalPredicates.INAMT_EQUAL_4090, predicate.toModelType());
    }

    @Test
    public void toModelType_validPredicateScript_returnsPredicate() throws Exception {
        JsonAdaptedPredicate predicate = new JsonAdaptedPredicateScript(
                (PredicateScript) TypicalPredicates.TEST_SCRIPT);
        assertEquals(TypicalPredicates.TEST_SCRIPT, predicate.toModelType());
    }

    @Test
    public void toModelType_invalidAttribute_throwsIllegalValueException() {
        JsonAdaptedPredicate predicate = new JsonAdaptedPredicateExpression(INVALID_ATTRIBUTE,
                VALID_OPERATOR, VALID_VALUE);
        String expectedMessage = Attribute.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, predicate::toModelType);
    }

    @Test
    public void toModelType_invalidOperator_throwsIllegalValueException() {
        JsonAdaptedPredicate predicate = new JsonAdaptedPredicateExpression(VALID_ATTRIBUTE,
                INVALID_OPERATOR, VALID_VALUE);
        String expectedMessage = Operator.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, predicate::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedPredicate predicate = new JsonAdaptedPredicateExpression(VALID_ATTRIBUTE,
                VALID_OPERATOR, INVALID_VALUE);
        String expectedMessage = Value.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, predicate::toModelType);
    }
}
