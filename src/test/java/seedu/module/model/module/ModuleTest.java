package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class ModuleTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
    private TrackedModule trackedModule = new TrackedModuleBuilder().withModule(archivedModule).build();

    /**
     * Tests whether 2 modules (tracked/archived) are the same.
     * They are the same modules if the module code matches.
     */
    @Test
    public void isSameModule() {
        assertTrue(trackedModule.isSameModule(archivedModule));

        ArchivedModule diffArchivedModule = new ArchivedModuleBuilder().withModuleCode("CS2101").build();
        TrackedModule diffTrackedModule = new TrackedModuleBuilder().withModule(diffArchivedModule).build();

        assertTrue(diffTrackedModule.isSameModule(diffArchivedModule));
        assertFalse(diffTrackedModule.isSameModule(archivedModule));
        assertFalse(trackedModule.isSameModule(diffArchivedModule));
    }

    @Test
    public void isTracked() {
        assertFalse(archivedModule.isTracked());
        assertTrue(trackedModule.isTracked());
    }
}
