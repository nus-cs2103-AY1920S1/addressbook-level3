package seedu.address.logic.rules;

import java.util.List;

/**
 * Represents a predicate written as an expression.
 */
public abstract class TestableExpression implements Testable {
    protected final List<String> args;

    /**
     * Constructs a TestableExpression given a list of arguments.
     *
     * @param args the arguments of the expression.
     */
    public TestableExpression(List<String> args) {
        this.args = args;
    }
}
