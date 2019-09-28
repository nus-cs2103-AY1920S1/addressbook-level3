package seedu.address.model;

import java.util.List;

/**
 * Represents information about a module.
 */
public class ModuleInfo {

    private String code;
    private int mc;
    private boolean su;
    private List<String> focusPrimaries;
    private List<String> focusElectives;
    private String description;
    private String prereqTreeString;

    public ModuleInfo() {
    }

    public ModuleInfo(String code, int mc, boolean su) {
        this.code = code;
        this.mc = mc;
        this.su = su;
    }

    public void parsePrereqTree() {
        // TODO
    }
}
