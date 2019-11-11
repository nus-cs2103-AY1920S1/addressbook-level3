package seedu.module.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;

/**
 * A utility class to help with building ArchivedModuleList objects.
 *
 * Defaults to empty list.
 */
public class ArchivedModuleListBuilder {
    private List<ArchivedModule> archivedModules;

    public ArchivedModuleListBuilder() {
        archivedModules = new ArrayList<>();
    }

    /**
     * Adds a {@code ArchivedModule} to the {@code ArchivedModuleList} that we are building.
     */
    public ArchivedModuleListBuilder withArchivedModule(ArchivedModule archivedModule) {
        this.archivedModules.add(archivedModule);
        return this;
    }

    /**
     * Builds and returns the archivedModuleList.
     */
    public ArchivedModuleList build() {
        ArchivedModuleList archivedModuleList = new ArchivedModuleList();
        for (ArchivedModule m : archivedModules) {
            archivedModuleList.add(m);
        }
        return archivedModuleList;
    }
}
