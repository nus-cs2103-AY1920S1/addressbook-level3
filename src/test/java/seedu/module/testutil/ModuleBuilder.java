package seedu.module.testutil;

import seedu.module.model.module.Module;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_TITLE = "Software Engineering";
    public static final String DEFAULT_DESCRIPTION = "Lorem Ipsum";

    private String moduleCode;
    private String title;
    private String description;

    public ModuleBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        title = moduleToCopy.getTitle();
        description = moduleToCopy.getDescription();
    }

    /**
     * Sets the moduleCode of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    /**
     * Sets the title of the {@code Module} that we are building.
     */
    public ModuleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the description of the {@code Module} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Module build() {
        return new Module(moduleCode, title, description);
    }
}
