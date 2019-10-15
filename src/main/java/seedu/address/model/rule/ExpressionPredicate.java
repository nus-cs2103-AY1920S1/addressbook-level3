package seedu.address.model.rule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a RulePredicate written as an expression.
 * Guarantees: operator and params are present and not null, field values are validated, immutable.
 */
public class ExpressionPredicate implements RulePredicate {
    private final Operator operator;
    private final List<Parameter> params = new ArrayList<>();

    /**
     * Constructs an {@code ExpressionPredicate}.
     *
     * @param operator the operator used in the expression.
     * @param params the params used in the expression.
     */
    public ExpressionPredicate(Operator operator, List<Parameter> params) {
        requireAllNonNull(operator, params);
        this.operator = operator;
        this.params.addAll(params);
    }

    public Operator getOperator() {
        return operator;
    }

    public List<Parameter> getParams() {
        return params;
    }

    /**
     * Returns true if both expression predicates have the same identity and detail fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpressionPredicate)) {
            return false;
        }

        ExpressionPredicate otherPred = (ExpressionPredicate) other;
        return otherPred.getOperator().equals(getOperator())
                && otherPred.getParams().equals(getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, params);
    }
}
