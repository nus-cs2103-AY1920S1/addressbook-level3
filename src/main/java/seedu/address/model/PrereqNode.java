package seedu.address.model;

import java.util.List;
import java.util.stream.Collectors;

public class PrereqNode extends PrereqTree {
    public static final int AND = 0;
    public static final int OR = 1;

    private final int operator;
    private final List<PrereqTree> children;

    public PrereqNode(String operator, List<PrereqTree> children) {
        if (operator.equals("AND")) {
            this.operator = AND;
        } else {
            this.operator = OR;
        }
        this.children = children;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public String toString() {
        return "(" + (this.operator == AND ? "AND" : "OR") + " " +
                children.stream().map(x -> x.toString()).collect(Collectors.joining(" ")) + ")";
    }
}
