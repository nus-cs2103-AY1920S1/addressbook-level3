package seedu.module.model.util;

import seedu.module.model.ModuleBook;
import seedu.module.model.module.TrackedModule;

/**
 * Contains utility methods for populating {@code ModuleBook} with sample data.
 */
public class SampleDataUtil {
    public static TrackedModule[] getSampleModules() {
        return new TrackedModule[]{
            new TrackedModule("CS2030", "Introduction to Java", "much java"),
            new TrackedModule("CS2103T", "SOFTENG", "much cancer")
        };
    }

    public static ModuleBook getSampleModuleBook() {
        ModuleBook sampleMb = new ModuleBook();
        for (TrackedModule sampleTrackedModule : getSampleModules()) {
            sampleMb.addModule(sampleTrackedModule);
        }
        return sampleMb;
    }
}
