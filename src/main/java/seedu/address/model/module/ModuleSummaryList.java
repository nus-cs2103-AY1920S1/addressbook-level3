package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * List of Modules.
 */
public class ModuleSummaryList {
    private List<ModuleSummary> moduleSummaries;

    public ModuleSummaryList() {
        this.moduleSummaries = new ArrayList<>();
    }

    public ModuleSummaryList(List<ModuleSummary> moduleSummaries) {
        this.moduleSummaries = moduleSummaries;
    }

    public void addModuleSummary(ModuleSummary moduleSummary) {
        this.moduleSummaries.add(moduleSummary);
    }

    /**
     * Finds a module summary using the academic year and module code, and returns the module summary.
     *
     * @param id a composite identifier based on academic year and module code.
     * @return module summary found
     */
    public ModuleSummary findModuleSummary(ModuleId id) {
        for (ModuleSummary moduleSummary : moduleSummaries) {
            if (moduleSummary.getModuleId().equals(id)) {
                return moduleSummary;
            }
        }
        throw new ModuleNotFoundException();
    }

    public List<ModuleSummary> getModuleSummaries() {
        return this.moduleSummaries;
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < moduleSummaries.size(); i++) {
            output += moduleSummaries.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Returns an unmodifiable observable list of Module Summaries.
     * @return ObservableList
     */
    public ObservableList<ModuleSummary> asUnmodifiableObservableList() {
        ObservableList<ModuleSummary> observableList = FXCollections.observableArrayList(moduleSummaries);
        return FXCollections.unmodifiableObservableList(observableList);
    }
}
