package seedu.address.model.module;

/**
 * Composite key composing of AcadYear and ModuleCode, which uniquely identifies a module.
 */
public class ModuleId {
    private final ModuleCode moduleCode;
    private final AcadYear acadYear;

    public ModuleId(String acadYear, String moduleCode) {
        this.acadYear = new AcadYear(acadYear);
        this.moduleCode = new ModuleCode(moduleCode);
    }

    public ModuleId(AcadYear acadYear, ModuleCode moduleCode) {
        this.acadYear = acadYear;
        this.moduleCode = moduleCode;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public AcadYear getAcadYear() {
        return acadYear;
    }

    @Override
    public String toString() {
        return acadYear + " " + moduleCode;
    }

    /**
     * Checks if this ModuleId is equal to other ModuleId.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(ModuleId other) {
        if (other == null) {
            return false;
        } else if (other.moduleCode.equals(this.moduleCode)
                && other.acadYear.equals(this.acadYear)) {
            return true;
        } else {
            return false;
        }
    }
}
