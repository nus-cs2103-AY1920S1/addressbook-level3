package seedu.module.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.module.model.module.Module;

/**
 * A list of modules to be displayed to the user.
 */
class DisplayedModuleList {
    private ObservableList<Module> internalObservableList = FXCollections.observableArrayList();

    public ObservableList<Module> getObservableList() {
        return internalObservableList;
    }

    /**
     * Adds a module to the list. If the module is already
     * in the list, the incoming module will override the one
     * in the list.
     */
    public void add(Module m) {
        int indexIfAny = this.indexOf(m);

        assert indexIfAny < internalObservableList.size() : "indexIfAny should not exceed list size";

        if (indexIfAny >= 0) {
            internalObservableList.set(indexIfAny, m);
            return;
        }

        internalObservableList.add(m);
    }

    /**
     * Clears the list.
     */
    public void clear() {
        internalObservableList.clear();
    }

    /**
     * Returns the index of the module {@code other}.
     * Returns -1 if not found.
     */
    private int indexOf(Module other) {
        int i = 0;
        boolean found = false;
        for (Module m : internalObservableList) {
            if (m.isSameModule(other)) {
                found = true;
                break;
            }

            i++;
        }

        return found ? i : -1;
    }
}
