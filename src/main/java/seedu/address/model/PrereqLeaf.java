package seedu.address.model;

import java.util.List;

/**
 * Leaf of a prerequisite tree. Represents a single module prerequisite.
 */
public class PrereqLeaf extends PrereqTree {
    private String code;

    public PrereqLeaf(String code) {
        this.code = code;
    }

    @Override
    public boolean verify(List<String> prevSemCodes) {
        return prevSemCodes.contains(this.code);
    }

    @Override
    public String toString() {
        return this.code;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PrereqLeaf)) { // this handles null as well
            return false;
        }

        PrereqLeaf o = (PrereqLeaf) other;
        return this.code.equals(o.code);
    }
}
