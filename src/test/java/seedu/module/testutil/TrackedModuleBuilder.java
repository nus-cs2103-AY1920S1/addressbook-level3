package seedu.module.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;

/**
 * A utility class to help with building TrackedModule objects.
 */
public class TrackedModuleBuilder {
    private ArchivedModule archivedModule;
    private List<Deadline> deadline;

    public TrackedModuleBuilder() {
        archivedModule = new ArchivedModuleBuilder().build();
        deadline = new ArrayList<>();
    }

    /**
     * Initializes the TrackedModuleBuilder with the data of {@code moduleToCopy}.
     */
    public TrackedModuleBuilder(TrackedModule moduleToCopy) {
        archivedModule = new ArchivedModuleBuilder().withModuleCode(moduleToCopy.getModuleCode())
            .withTitle(moduleToCopy.getTitle())
            .withDescription(moduleToCopy.getDescription())
            .build();
        deadline = moduleToCopy.getDeadlineList();
    }

    /**
     * Sets the archivedModule of the {@code TrackedModule} that we are building.
     */
    public TrackedModuleBuilder withModule(ArchivedModule archivedModule) {
        this.archivedModule = archivedModule;
        return this;
    }

    /**
     * Sets the description of the {@code TrackedModule} that we are building.
     */
    public TrackedModuleBuilder withDeadline(List<Deadline> deadline) {
        this.deadline = deadline;
        return this;
    }

    public TrackedModule build() {
        return new TrackedModule(archivedModule);
    }
}
