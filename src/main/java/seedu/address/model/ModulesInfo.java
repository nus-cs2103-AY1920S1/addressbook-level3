package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a collection of module information.
 */
public class ModulesInfo {
    private HashMap<String, ModuleInfo> mapModulesInfo; // this is actually what's being used
    private List<ModuleInfo> modulesInfo; // this is just for reading from JSON

    public ModulesInfo() {
        this.modulesInfo = new ArrayList<>();
        this.mapModulesInfo = new HashMap<>();
    }

    public ModulesInfo(HashMap<String, ModuleInfo> modulesInfo) {
        this.modulesInfo = new ArrayList<>();
        this.mapModulesInfo = modulesInfo;
    }

    /**
     * Initialises by reading {@code modulesInfo} into #{@code mapModulesInfo}.
     * Parses the prerequisite tree for all modules with information.
     */
    public void init() {
        this.mapModulesInfo = new HashMap<>();
        for (ModuleInfo moduleInfo : modulesInfo) {
            moduleInfo.init();
            this.mapModulesInfo.put(moduleInfo.getCode(), moduleInfo);
        }
    }

    /**
     * Finds a specific module information that has the given module code.
     * @param moduleCode Module code
     * @return Module information
     */
    public ModuleInfo find(String moduleCode) {
        return this.mapModulesInfo.get(moduleCode);
    }

    /**
     * Returns true if the module with the given module code can have its prerequisites fulfilled by taking
     * modules in {@code prevSemModuleCodes} in previous semesters.
     */
    public boolean verify(String moduleCode, List<String> prevSemModuleCodes) {
        ModuleInfo moduleInfo = this.find(moduleCode);
        if (moduleInfo == null) {
            return false;
        }
        return moduleInfo.verify(prevSemModuleCodes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModulesInfo // instanceof handles nulls
                && mapModulesInfo.equals(((ModulesInfo) other).mapModulesInfo));
    }

    @Override
    public int hashCode() {
        return mapModulesInfo.hashCode();
    }
}
