package budgetbuddy.logic.rules;

import budgetbuddy.model.Direction;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Operator;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Contains utility methods and constants used for processing rules.
 */
public class RuleProcessingUtil {
    public static final String TYPE_DESC = "DESCRIPTION";
    public static final String TYPE_AMOUNT = "AMOUNT";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_CATEGORY = "CATEGORY";

    /**
     * Is a private constructor for a static-only class.
     */
    private RuleProcessingUtil() {}

    /**
     * Returns the value of a transaction's attribute given the transaction.
     */
    public static Object extractAttribute(Attribute attribute, Transaction txn) {
        switch (attribute) {
        case DESCRIPTION:
            return txn.getDescription();
        case AMOUNT:
            long sign = txn.getDirection().equals(Direction.IN) ? 1 : -1;
            return sign * txn.getAmount().toLong();
        case DATE:
            return txn.getDate();
        default:
            // impossible
            assert false : "Unhandled attribute";
            return null;
        }
    }

    /**
     * Returns if a value can be parsed into the specified type.
     */
    public static boolean isValueParsable(String typeName, Value value) {
        switch (typeName) {
        case TYPE_DESC:
        case TYPE_CATEGORY:
            // don't have to handle since value already stored as string
            break;
        case TYPE_AMOUNT:
            try {
                Long.parseLong(value.toString());
                break;
            } catch (NumberFormatException e) {
                return false;
            }
        case TYPE_DATE:
            // todo: need to try parsing date
            return false;
        default:
            return false;
        }

        return true;
    }

    /**
     * Returns if the predicate expression is valid,
     * i.e. attribute and value are all working with the expected type specified by the operator.
     */
    public static boolean isValidPredicateExpr(Attribute attribute, Operator operator, Value value) {
        return operator.getExpectedType().equals(attribute.getEvaluatedType())
                && isValueParsable(operator.getExpectedType(), value);
    }

    /**
     * Returns if the action expression is valid,
     * i.e. value is working with the expected type specified by the operator.
     */
    public static boolean isValidActionExpr(Operator operator, Value value) {
        return isValueParsable(operator.getExpectedType(), value);
    }
}
