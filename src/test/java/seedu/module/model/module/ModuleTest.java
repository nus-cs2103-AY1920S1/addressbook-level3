package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class ModuleTest {

    private ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
    private TrackedModule trackedModule = new TrackedModuleBuilder().withModule(archivedModule).build();

    @Test
    public void isTracked() {
        assertFalse(archivedModule.isTracked());
        assertTrue(trackedModule.isTracked());
    }
}
