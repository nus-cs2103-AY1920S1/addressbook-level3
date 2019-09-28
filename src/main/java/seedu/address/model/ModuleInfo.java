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

    public ModuleInfo(String code, int mc, boolean su, List<String> focusPrimaries, List<String> focusElectives,
                      String description, String prereqTreeString) {
        this.code = code;
        this.mc = mc;
        this.su = su;
        this.focusPrimaries = focusPrimaries;
        this.focusElectives = focusElectives;
        this.description = description;
        this.prereqTreeString = prereqTreeString;
    }

    public void parsePrereqTree() {
        // TODO
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModuleInfo)) { // this handles null as well
            return false;
        }

        ModuleInfo o = (ModuleInfo) other;

        return code.equals(o.code)
                && mc == o.mc
                && su == o.su
                && focusPrimaries.equals(o.focusPrimaries)
                && focusElectives.equals(o.focusElectives)
                && description.equals(o.description)
                && prereqTreeString.equals(o.prereqTreeString);
    }
}
