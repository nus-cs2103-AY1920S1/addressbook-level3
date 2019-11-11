package seedu.module.testutil;

import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;

/**
 * A utility class to help with building Modulebook objects.
 *
 * The default ModuleBook comes with a list of 1 {@code ArchivedModule}.
 */
public class ModuleBookBuilder {

    private ModuleBook moduleBook;

    public ModuleBookBuilder() {
        moduleBook = new ModuleBook();
        moduleBook.setArchivedModules(new ArchivedModuleListBuilder()
            .withArchivedModule(new ArchivedModuleBuilder().build())
            .build());
    }

    public ModuleBookBuilder(ModuleBook moduleBook) {
        this.moduleBook = moduleBook;
    }

    /**
     * Adds a new {@code Module} to the {@code ModuleBook} that we are building.
     */
    public ModuleBookBuilder withTrackedModule(TrackedModule trackedModule) {
        moduleBook.addModule(trackedModule);
        return this;
    }

    /**
     * Sets the {@code ArchivedModuleList} of the {@code ModuleBook} that we are building.
     */
    public ModuleBookBuilder withArchivedModules(ArchivedModuleList archivedModules) {
        moduleBook.setArchivedModules(archivedModules);
        return this;
    }

    public ModuleBook build() {
        return moduleBook;
    }
}
