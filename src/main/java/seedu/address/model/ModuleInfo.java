package seedu.address.model;

import java.util.List;

import seedu.address.logic.parser.ParserUtil;

/**
 * Represents information about a module.
 */
public class ModuleInfo {

    private String code;
    private String name;
    private int mc;
    private boolean su;
    private boolean isCore;
    private List<String> focusPrimaries;
    private List<String> focusElectives;
    private String description;
    private String prereqTreeString; // this is for reading from JSON
    private PrereqTree prereqTree; // this is actually what's being used

    public ModuleInfo() {
    }

    public ModuleInfo(String code, String name, int mc, boolean su, boolean isCore, List<String> focusPrimaries,
                      List<String> focusElectives, String description, String prereqTreeString) {
        this.code = code;
        this.name = name;
        this.mc = mc;
        this.su = su;
        this.isCore = isCore;
        this.focusPrimaries = focusPrimaries;
        this.focusElectives = focusElectives;
        this.description = description;
        this.prereqTreeString = prereqTreeString;
        this.prereqTree = null;
        init();
    }

    /**
     * This method should be called to create {@code prereqTree} after ModuleInfo is created or read from JSON
     */
    public void init() {
        this.prereqTree = ParserUtil.parsePrereqTree(this.prereqTreeString);
    }

    public PrereqTree getPrereqTree() {
        return this.prereqTree;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return name;
    }

    public int getMc() {
        return mc;
    }

    public boolean getIsCore() {
        return isCore;
    }

    public List<String> getFocusPrimaries() {
        return this.focusPrimaries;
    }

    public List<String> getFocusElectives() {
        return this.focusElectives;
    }

    public boolean getSuEligibility() {
        return su;
    }

    /**
     * Returns a String displaying all information about the module, in a human-readable format.
     */
    public String getInformation() {
        return this.code + ": " + this.name + (this.isCore ? " (Core module)" : "") + "\n"
                + this.mc + " MCs, " + (this.su ? "" : "not ") + "S/U-able" + "\n"
                + "Prerequisites: " + (this.prereqTree == null ? "none" : this.prereqTree) + "\n"
                + this.description;
    }

    public boolean verify(List<String> prevSemModuleCodes) {
        return prereqTree.verify(prevSemModuleCodes);
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
                && name.equals(o.name)
                && mc == o.mc
                && su == o.su
                && isCore == o.isCore
                && focusPrimaries.equals(o.focusPrimaries)
                && focusElectives.equals(o.focusElectives)
                && description.equals(o.description)
                && prereqTreeString.equals(o.prereqTreeString)
                && prereqTree.equals(o.prereqTree);
    }
}
