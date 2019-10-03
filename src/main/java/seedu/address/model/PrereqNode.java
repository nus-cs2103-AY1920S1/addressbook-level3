package seedu.address.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Node of a prerequisite tree. Represents an operator (AND/OR) with children, which are themselves prerequisite trees.
 */
public class PrereqNode extends PrereqTree {
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
        return "(" + (this.operator == AND ? "AND" : "OR") + " "
                + children.stream().map(x -> x.toString()).collect(Collectors.joining(" ")) + ")";
    }
}
