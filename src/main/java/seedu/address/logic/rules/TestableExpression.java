package seedu.address.logic.rules;

import java.util.List;

import seedu.address.model.rule.Parameter;

/**
 * Represents a predicate written as an expression.
 */
public abstract class TestableExpression implements Testable {
    protected final List<Parameter> params;

    /**
     * Constructs a TestableExpression given a list of parameters.
     *
     * @param params the parameters of the expression.
     */
    public TestableExpression(List<Parameter> params) {
        this.params = params;
    }
}
