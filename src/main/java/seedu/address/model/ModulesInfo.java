package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of module information.
 */
public class ModulesInfo {
    private List<ModuleInfo> modulesInfo;

    public ModulesInfo() {
        this.modulesInfo = new ArrayList<>();
    }

    public ModulesInfo(List<ModuleInfo> modulesInfo) {
        this.modulesInfo = modulesInfo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModulesInfo // instanceof handles nulls
                && modulesInfo.equals(((ModulesInfo) other).modulesInfo));
    }

    @Override
    public int hashCode() {
        return modulesInfo.hashCode();
    }
}
