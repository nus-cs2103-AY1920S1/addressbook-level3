package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.prereqTree = parsePrereqTree(this.prereqTreeString);
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

    /**
     * Returns a String displaying all information about the module, in a human-readable format.
     */
    public String getInformation() {
        return this.code + ": " + this.name + (this.isCore ? "(Core module)" : "") + "\n"
                + this.mc + " MCs, " + (this.su ? "" : "not ") + "S/U-able" + "\n"
                + this.description;
    }

    /**
     * Parses the prerequisite tree from a given string.
     * @param s Given string representing the prerequisite tree. Either the empty string, a module code, or (OP _ _).
     * @return Prerequisite tree
     */
    private PrereqTree parsePrereqTree(String s) {
        if ("".equals(s)) {
            return null;
        } else if (s.charAt(0) != '(') {
            return new PrereqLeaf(s);
        }

        // Split the string into list of items
        String removeBrackets = s.substring(1, s.length() - 1);
        String[] operatorOperands = removeBrackets.split(" ", 2);
        String operator = operatorOperands[0];
        String operandsString = operatorOperands[1];
        List<String> operands = splitOperands(operandsString);
        List<PrereqTree> children = operands.stream()
                .map(operand -> parsePrereqTree(operand))
                .collect(Collectors.toList());
        return new PrereqNode(operator, children);
    }

    /**
     * Splits the String of operands into its logical groupings.
     * Example: "CS1 (AND (OR CS2 CS3) CS4) CS5" => ["CS1", "(AND (OR CS2 CS3) CS4)", "CS5"]
     * @param operands String that represents the operands all together
     * @return List of Strings, where each represents a single operand to be further parsed
     */
    private List<String> splitOperands(String operands) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int balance = 0;
        for (char c : operands.toCharArray()) {
            sb.append(c);
            if (c == ' ' && balance == 0) {
                String trimmed = sb.toString().trim();
                if (!"".equals(trimmed)) {
                    list.add(trimmed);
                }
                sb.setLength(0);
            } else if (c == '(') {
                balance++;
            } else if (c == ')' && --balance == 0) {
                list.add(sb.toString().trim());
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
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
