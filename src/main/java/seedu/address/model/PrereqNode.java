package seedu.address.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Node of a prerequisite tree. Represents an operator (AND/OR) with children, which are themselves prerequisite trees.
 */
public class PrereqNode implements PrereqTree {
    public static final int AND = 0;
    public static final int OR = 1;

    private final int operator;
    private final List<PrereqTree> children;

    public PrereqNode(String operator, List<PrereqTree> children) {
        if ("AND".equals(operator)) {
            this.operator = AND;
        } else {
            this.operator = OR;
        }
        this.children = children;
    }

    @Override
    public boolean verify(List<String> prevSemCodes) {
        Stream<Boolean> verifyStream = this.children
                .stream()
                .map(child -> child.verify(prevSemCodes));
        if (this.operator == AND) {
            return verifyStream.reduce(true, (x, y) -> x && y);
        } else {
            return verifyStream.reduce(false, (x, y) -> x || y);
        }
    }

    @Override
    public String toString() {
        String delimiter = this.operator == AND ? " and " : " or ";
        return children
                .stream()
                .map(x -> (x instanceof PrereqNode) ? "(" + x.toString() + ")" : x.toString())
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PrereqNode)) { // this handles null as well
            return false;
        }

        PrereqNode o = (PrereqNode) other;

        return this.operator == o.operator
                && this.children.equals(o.children);
    }
}
