package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.TrackedModuleBuilder;

public class TrackedModuleTest {

    @Test
    public void isSameModule() {
        // same values -> returns true
        TrackedModule trackedModule = new TrackedModuleBuilder().build();
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder(trackedModule).build()));

        // same object -> returns true
        assertTrue(trackedModule.isSameModule(trackedModule));

        // null -> returns false
        assertFalse(trackedModule.isSameModule(null));

        // different moduleCode -> returns false
        assertFalse(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withModuleCode("CS1101S").build()));

        // different title -> returns true
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withTitle("Different Title").build()));

        // different description -> returns true
        assertTrue(trackedModule.isSameModule(new TrackedModuleBuilder()
                .withDescription("The quick brown fox jumps over the lazy dog").build()));
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
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
                .withModuleCode("CS1101S").build()));

        // different title -> returns false
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
                .withTitle("Different Title").build()));

        // different description -> returns false
        assertFalse(trackedModule.equals(new TrackedModuleBuilder()
                .withDescription("The quick brown fox jumps over the lazy dog").build()));
    }
}
