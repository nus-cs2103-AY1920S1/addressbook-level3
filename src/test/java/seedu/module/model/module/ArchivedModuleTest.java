package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.ArchivedModuleBuilder;

public class ArchivedModuleTest {

    @Test
    public void isSameModule() {
        // same values -> returns true
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        assertTrue(archivedModule.isSameModule(new ArchivedModuleBuilder(archivedModule).build()));

        // same object -> returns true
        assertTrue(archivedModule.isSameModule(archivedModule));

        // null -> returns false
        assertFalse(archivedModule.isSameModule(null));

        // different moduleCode -> returns false
        assertFalse(archivedModule.isSameModule(new ArchivedModuleBuilder()
            .withModuleCode("CS1101S").build()));

        // different title -> returns true
        assertTrue(archivedModule.isSameModule(new ArchivedModuleBuilder()
            .withTitle("Different Title").build()));

        // different description -> returns true
        assertTrue(archivedModule.isSameModule(new ArchivedModuleBuilder()
            .withDescription("The quick brown fox jumps over the lazy dog").build()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        assertTrue(archivedModule.equals(new ArchivedModuleBuilder(archivedModule).build()));

        // same object -> returns true
        assertTrue(archivedModule.equals(archivedModule));

        // null -> returns false
        assertFalse(archivedModule.equals(null));

        // different type -> returns false
        assertFalse(archivedModule.equals(5));

        // different moduleCode -> returns false
        assertFalse(archivedModule.equals(new ArchivedModuleBuilder()
            .withModuleCode("CS1101S").build()));

        // different title -> returns false
        assertFalse(archivedModule.equals(new ArchivedModuleBuilder()
            .withTitle("Different Title").build()));

        // different description -> returns false
        assertFalse(archivedModule.equals(new ArchivedModuleBuilder()
            .withDescription("The quick brown fox jumps over the lazy dog").build()));
    }
}
