package seedu.address.model;

import java.util.List;

/**
 * Leaf of a prerequisite tree. Represents a single module prerequisite.
 */
public class PrereqLeaf implements PrereqTree {
    private String moduleCode;

    public PrereqLeaf(String code) {
        this.moduleCode = code;
    }

    @Override
    public boolean verify(List<String> prevSemCodes) {
        return prevSemCodes.contains(this.moduleCode);
    }

    @Override
    public String toString() {
        return this.moduleCode;
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
        return this.moduleCode.equals(o.moduleCode);
    }
}
