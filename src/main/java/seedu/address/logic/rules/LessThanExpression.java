package seedu.address.logic.rules;

import java.util.List;

import seedu.address.model.rule.Parameter;

/**
 * Represents a less-than expression.
 */
public class LessThanExpression extends TestableExpression {
    /**
     * Constructs a LessThanExpression with the given parameters.
     *
     * @param params the parameters of the expression.
     */
    public LessThanExpression(List<Parameter> params) {
        super(params);
    }

    @Override
    public boolean test(Object o) {
        Parameter leftParam = params.get(0);
        Parameter rightParam = params.get(1);
        return false;
    }
}
