package seedu.address.model;

import java.util.List;

/**
 * Tree of prerequisites, either a leaf or a node.
 */
public abstract class PrereqTree {
    public abstract boolean verify(List<String> prevSemCodes);
}
