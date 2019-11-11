
package seedu.module.model.util;

import java.util.List;
import java.util.stream.Collectors;

import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;

/**
 * Contains utility methods for populating {@code ModuleBook} with sample data.
 */
public class SampleDataUtil {
    private static final List<String> moduleCodes = List.of(
        "CS1101S",
        "CS2030",
        "CS2040",
        "CS2101",
        "CS2103T"
    );

    private static final List<Deadline> deadlines = List.of(
        new Deadline("Assignment 3", "9/11/2019 2359", "HIGH"),
        new Deadline("Lab 10", "12/11/2019 0900", "MEDIUM"),
        new Deadline("Tutorial 7", "7/11/2019 1200", "LOW")
    );

    private static final List<Link> links = List.of(
        new Link("Luminus Website", "https://luminus.nus.edu.sg/"),
        new Link("Nusmods Timetable", "https://nusmods.com/timetable/sem-1")
    );

    /**
     * Generates and returns a sample module book.
     * @param archivedModules the archived modules to generate modules off of
     * @implNote this sample module book relies on the modules already existing as archived modules.
     */
    public static ReadOnlyModuleBook getSampleModuleBook(ArchivedModuleList archivedModules) {
        ModuleBook sampleMb = new ModuleBook(archivedModules);
        List<ArchivedModule> modulesToAdd = archivedModules.asUnmodifiableObservableList().stream()
            .filter(module -> moduleCodes.contains(module.getModuleCode()))
            .collect(Collectors.toList());
        for (ArchivedModule moduleToAdd : modulesToAdd) {
            TrackedModule trackedModule = new TrackedModule(moduleToAdd);
            for (Deadline deadline : deadlines) {
                trackedModule.addDeadline(deadline);
            }

            for (Link link : links) {
                trackedModule.addLink(link);
            }

            sampleMb.addModule(trackedModule);
        }
        return sampleMb;
    }
}
