package seedu.address.model.module;

public class ModuleCode {
    private String moduleCode;

    public ModuleCode(String moduleCode){
        this.moduleCode = moduleCode;
    }

    @Override
    public String toString() {
        return moduleCode;
    }
}
