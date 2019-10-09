package seedu.module.testutil;

import seedu.module.model.ModuleBook;
import seedu.module.model.module.Module;

/**
 * A utility class to help with building Modulebook objects.
 * Example usage: <br>
 * {@code ModuleBook ab = new ModuleBookBuilder().withModule("John", "Doe").build();}
 */
public class ModuleBookBuilder {

    private ModuleBook moduleBook;

    public ModuleBookBuilder() {
        moduleBook = new ModuleBook();
    }

    public ModuleBookBuilder(ModuleBook moduleBook) {
        this.moduleBook = moduleBook;
    }

    /**
     * Adds a new {@code Module} to the {@code ModuleBook} that we are building.
     */
    public ModuleBookBuilder withModule(Module module) {
        moduleBook.addModule(module);
        return this;
    }

    public ModuleBook build() {
        return moduleBook;
    }
}
