package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class TrackedModuleTest {
    private static final ArchivedModule ARCHIVED_MODULE = new ArchivedModuleBuilder().build();

    @Test
    public void isSameModule() {
        // same values -> returns true
        TrackedModule trackedModule = new TrackedModuleBuilder().withModule(ARCHIVED_MODULE).build();
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder(trackedModule).build()));

        // same object -> returns true
        assertTrue(trackedModule.isSameModule(trackedModule));

        // null -> returns false
        assertFalse(trackedModule.isSameModule(null));

        // different moduleCode -> returns false
        ArchivedModule archivedModuleDifferentModuleCode = new ArchivedModuleBuilder()
            .withModuleCode("CS1101S")
            .build();
        assertFalse(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withModule(archivedModuleDifferentModuleCode).build()));

        // different title -> returns true
        ArchivedModule archivedModuleDifferentTitle = new ArchivedModuleBuilder()
            .withTitle("Different Title")
            .build();
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withModule(archivedModuleDifferentTitle).build()));

        // different description -> returns true
        ArchivedModule archivedModuleDifferentDescription = new ArchivedModuleBuilder()
            .withDescription("Definitely different description")
            .build();
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withModule(archivedModuleDifferentDescription).build()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        assertTrue(trackedModule.equals(new TrackedModuleBuilder(trackedModule).build()));

        // null -> returns false
        assertFalse(trackedModule.equals(null));

        // different type -> returns false
        assertFalse(trackedModule.equals(5));

        // different moduleCode -> returns false
        ArchivedModule archivedModuleDifferentModuleCode = new ArchivedModuleBuilder()
            .withModuleCode("CS1101S")
            .build();
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
            .withModule(archivedModuleDifferentModuleCode).build()));

        // different title -> returns false
        ArchivedModule archivedModuleDifferentTitle = new ArchivedModuleBuilder()
            .withTitle("Different Title")
            .build();
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
            .withModule(archivedModuleDifferentTitle).build()));

        // different description -> returns false
        ArchivedModule archivedModuleDifferentDescription = new ArchivedModuleBuilder()
            .withDescription("Definitely different description")
            .build();
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
            .withModule(archivedModuleDifferentDescription).build()));
    }
}
