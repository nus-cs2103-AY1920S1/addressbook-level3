package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * List of Modules.
 */
public class ModuleCondensedList {
    private List<ModuleCondensed> moduleCondensedList;

    public ModuleCondensedList() {
        this.moduleCondensedList = new ArrayList<>();
    }

    /**
     * Finds a condensed module using the academic year and module code, and returns the condensed module.
     *
     * @param acadYear academic year
     * @param code of the condensed module to find
     * @return condensed module found
     */
    public ModuleCondensed findModuleCondensed(AcadYear acadYear, ModuleCode code) {
        for (ModuleCondensed moduleCondensed : moduleCondensedList) {
            if (moduleCondensed.getAcadYear().equals(acadYear) && moduleCondensed.getModuleCode().equals(code)) {
                return moduleCondensed;
            }
        }
        throw new ModuleNotFoundException();
    }

    public List<ModuleCondensed> getModuleCondensedList() {
        return moduleCondensedList;
    }

    public void setModuleCondensedList(List<ModuleCondensed> moduleCondensedList) {
        this.moduleCondensedList = moduleCondensedList;
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < moduleCondensedList.size(); i++) {
            output += moduleCondensedList.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Returns an unmodifiable observable list of Condensed Modules.
     * @return ObservableList
     */
    public ObservableList<ModuleCondensed> asUnmodifiableObservableList() {
        ObservableList<ModuleCondensed> observableList = FXCollections.observableArrayList(moduleCondensedList);
        return FXCollections.unmodifiableObservableList(observableList);
    }
}
