package seedu.address.model.module;

import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ModuleCode)) {
            return false;
        }
        ModuleCode mc = (ModuleCode) other;
        if (mc == this) {
            return true;
        } else if (mc.moduleCode.equals(this.moduleCode)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode);
    }
}
