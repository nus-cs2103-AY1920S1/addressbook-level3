package seedu.address.logic.rules;

import seedu.address.model.Direction;
import seedu.address.model.rule.expression.Attribute;
import seedu.address.model.rule.expression.Operator;
import seedu.address.model.rule.expression.Value;
import seedu.address.model.transaction.Transaction;

/**
 * Contains utility methods and constants used for processing rules.
 */
public class RuleProcessingUtil {
    public static final String TYPE_DESC = "DESC";
    public static final String TYPE_AMOUNT = "AMOUNT";
    public static final String TYPE_DATE = "DATE";

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

    public static boolean isValueParsable(String typeName, Value value) {
        switch (typeName) {
        case TYPE_AMOUNT:
            try {
                Long.parseLong(value.toString());
                break;
            } catch (NumberFormatException e) {
                return false;
            }
        case TYPE_DESC:
            // don't have to handle since value already stored as string
            break;
        case TYPE_DATE:
            // todo: need to try parsing date
            return false;
        default:
            return false;
        }

        return true;
    }

    public static boolean isValidExpression(Attribute attribute, Operator operator, Value value) {
        return operator.getExpectedType().equals(attribute.getEvaluatedType())
                && isValueParsable(operator.getExpectedType(), value);
    }
}
