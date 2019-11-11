package seedu.address.model;

import java.util.List;

/**
 * Tree of prerequisites, either a leaf or a node.
 */
public interface PrereqTree {
    boolean verify(List<String> prevSemCodes);
}
