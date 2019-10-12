package seedu.module.model.util;

import seedu.module.model.ModuleBook;
import seedu.module.model.module.Module;

/**
 * Contains utility methods for populating {@code ModuleBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[]{
            new Module("CS2030", "Introduction to Java", "much java"),
            new Module("CS2103T", "SOFTENG", "much cancer")
        };
    }

    public static ModuleBook getSampleModuleBook() {
        ModuleBook sampleMb = new ModuleBook();
        for (Module sampleModule : getSampleModules()) {
            sampleMb.addModule(sampleModule);
        }
        return sampleMb;
    }
}
