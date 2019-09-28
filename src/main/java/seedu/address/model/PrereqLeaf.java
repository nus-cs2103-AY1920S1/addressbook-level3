package seedu.address.model;

public class PrereqLeaf extends PrereqTree {
    private String code;

    public PrereqLeaf(String code) {
        this.code = code;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
