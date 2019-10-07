package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleList;

/**
 * Contains all the information the app needs from NUSMods.
 */
public class NusModsData {

    private ModuleList moduleList;

    public NusModsData() {
        this.moduleList = new ModuleList();
    }

    public NusModsData(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public void addModule(Module module) {
        this.moduleList.addModule(module);
    }

    public ModuleList getModuleList() {
        return this.moduleList;
    }

    public ObservableList<Module> getUnmodifiableModuleList() {
        return moduleList.asUnmodifiableObservableList();
    }
}
