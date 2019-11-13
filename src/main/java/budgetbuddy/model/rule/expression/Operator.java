package budgetbuddy.model.rule.expression;

import static budgetbuddy.logic.rules.RuleEngine.TYPE_AMOUNT;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_BLANK;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_CATEGORY;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_DATE;
import static budgetbuddy.logic.rules.RuleEngine.TYPE_DESC;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an Operator in an expression.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    // Predicate operators
    LESS_THAN("<", "<", TYPE_DESC, TYPE_DATE, TYPE_AMOUNT),
    MORE_THAN(">", ">", TYPE_DESC, TYPE_DATE, TYPE_AMOUNT),
    LESS_EQUAL("<=", "<=", TYPE_DESC, TYPE_DATE, TYPE_AMOUNT),
    MORE_EQUAL(">=", ">=", TYPE_DESC, TYPE_DATE, TYPE_AMOUNT),
    EQUAL_TO("=", "=", TYPE_DESC, TYPE_DATE, TYPE_AMOUNT),
    CONTAINS("contains", "contains", TYPE_DESC, TYPE_AMOUNT),

    // Action operators
    SET_CATEGORY("set_cat", "set category", TYPE_CATEGORY),
    REMOVE_CATEGORY("remove_cat", "remove category", TYPE_CATEGORY),
    SET_DESC("set_desc", "set description", TYPE_DESC),
    APPEND_DESC("app_desc", "append desc", TYPE_DESC),
    PREPEND_DESC("prep_desc", "prepend desc", TYPE_DESC),
    SET_IN("set_in", "set txn inwards", TYPE_BLANK),
    SET_OUT("set_out", "set txn outwards", TYPE_BLANK),
    SWITCH_DIRECTION("switch_direct", "switch txn direction", TYPE_BLANK);

    public static final String MESSAGE_CONSTRAINTS =
            "Operators should be valid for their expression and not be blank\n"
            + "Valid operators: "
            + Arrays.stream(Operator.values())
                    .map(op -> op.opWord)
                    .reduce((x, y) -> x + ", " + y)
                    .orElse("");

    private final String opWord;
    private final String rep;
    private final Set<String> expectedTypes = new HashSet<>();

    Operator(String opWord, String rep, String... expectedType) {
        this.opWord = opWord;
        this.rep = rep;
        this.expectedTypes.addAll(Arrays.asList(expectedType));
    }

    /**
     * Returns true if a given string is a valid operator.
     */
    public static boolean isValidOperator(String test) {
        return Arrays
                .stream(Operator.values())
                .anyMatch(operator -> operator.opWord.equals(test.toLowerCase()));
    }

    /**
     * Returns an {@code Operator} given a valid representation.
     */
    public static Operator of(String opWord) {
        return Arrays
                .stream(Operator.values())
                .filter(operator -> operator.opWord.equals(opWord.toLowerCase()))
                .findFirst()
                .get();
    }

    /**
     * Returns the set of types that this operator expect from its arguments.
     */
    public Set<String> getExpectedTypes() {
        return Collections.unmodifiableSet(expectedTypes);
    }

    public String getOperatorWord() {
        return opWord;
    }

    @Override
    public String toString() {
        return rep;
    }
}
