package seedu.address.model.module;

/**
 * The module code of the module
 */
public class ModuleCode {
    private String moduleCode;

    public ModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    /**
     * Checks if this ModuleCode is equal to other ModuleCode.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(ModuleCode other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.moduleCode)) {
            return true;
        } else {
            return false;
        }
    }
}
