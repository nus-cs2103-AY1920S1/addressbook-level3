package seedu.module.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * A utility class to help with building TrackedModule objects.
 */
public class TrackedModuleBuilder {

    private ArchivedModule archivedModule;
    private List<Deadline> deadline;
    private List<Link> links;

    public TrackedModuleBuilder() {
        archivedModule = new ArchivedModuleBuilder().build();
        deadline = new ArrayList<>();
        links = new ArrayList<>();
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
        links = moduleToCopy.getLink();
    }

    /**
     * Sets the archivedModule of the {@code TrackedModule} that we are building.
     */
    public TrackedModuleBuilder withModule(ArchivedModule archivedModule) {
        this.archivedModule = archivedModule;
        return this;
    }

    /**
     * Sets the deadline of the {@code TrackedModule} that we are building.
     */
    public TrackedModuleBuilder withDeadline(List<Deadline> deadline) {
        this.deadline = deadline;
        return this;
    }

    /**
     * Sets the links of the {@code TrackedModule} that we are building.
     * @return
     */
    public TrackedModuleBuilder withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    /**
     * Returns the TrackedModule with the built information
     * @return
     */
    public TrackedModule build() {
        TrackedModule result = new TrackedModule(archivedModule);
        for (Link link: links) {
            result.addLink(link);
        }
        for (Deadline dl: deadline) {
            result.addDeadline(dl);
        }
        return result;
    }
}
