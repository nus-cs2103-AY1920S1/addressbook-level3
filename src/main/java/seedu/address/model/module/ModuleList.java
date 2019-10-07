package seedu.address.model.module;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * Finds a module with code and returns the module.
     *
     * @param code of the module to find
     * @return module found
     */
    public Module findModule(ModuleCode code) {
        int i;
        for (i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleCode().equals(code)) {
                return modules.get(i);
            }
        }
        return null;
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

    public ArrayList<Module> getModules() {
        return this.modules;
    }
}
