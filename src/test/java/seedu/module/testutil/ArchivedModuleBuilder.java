package seedu.module.testutil;

import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.SemesterDetailList;

/**
 * A utility class to help with building ArchivedModule objects.
 */
public class ArchivedModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_TITLE = "Software Engineering";
    public static final String DEFAULT_DESCRIPTION = "Lorem Ipsum";

    private String moduleCode;
    private String title;
    private String description;
    private String prerequisite;
    private String preclusion;
    private SemesterDetailList semesterDetails;

    public ArchivedModuleBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        semesterDetails = new SemesterDetailList();
    }

    /**
     * Initializes the ArchivedModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ArchivedModuleBuilder(ArchivedModule moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        title = moduleToCopy.getTitle();
        description = moduleToCopy.getDescription();
    }

    /**
     * Sets the moduleCode of the {@code ArchivedModule} that we are building.
     */
    public ArchivedModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    /**
     * Sets the title of the {@code ArchivedModule} that we are building.
     */
    public ArchivedModuleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the description of the {@code ArchivedModule} that we are building.
     */
    public ArchivedModuleBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the prerequisite of the {@code ArchivedModule} that we are building.
     */
    public ArchivedModuleBuilder withPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
        return this;
    }

    /**
     * Constructs and returns the {@code ArchivedModule}.
     */
    public ArchivedModule build() {
        return new ArchivedModule(moduleCode, title, description, prerequisite,
            preclusion, semesterDetails);
    }
}
