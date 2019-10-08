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
}
