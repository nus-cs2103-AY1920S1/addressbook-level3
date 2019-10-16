package seedu.address.model.module;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * List of Modules.
 */
public class ModuleList {
    private ArrayList<Module> modules;

    public ModuleList() {
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    /**
     * Finds a module using the academic year and module code, and returns the module.
     *
     * @param id a composite identifier based on academic year and module code.
     * @return module found
     */
    public Module findModule(ModuleId id) {
        for (Module module : modules) {
            if (module.getModuleId().equals(id)) {
                return module;
            }
        }
        throw new ModuleNotFoundException();
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < modules.size(); i++) {
            output += modules.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Returns an unmodifiable observable list of Modules.
     * @return ObservableList
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        ObservableList<Module> observableList = FXCollections.observableArrayList(modules);
        return FXCollections.unmodifiableObservableList(observableList);
    }
}
