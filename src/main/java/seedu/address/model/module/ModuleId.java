package seedu.address.model.module;

import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ModuleId)) {
            return false;
        }
        ModuleId id = (ModuleId) other;
        if (id == this) {
            return true;
        } else if (id.moduleCode.equals(this.moduleCode)
                && id.acadYear.equals(this.acadYear)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, acadYear);
    }
}
