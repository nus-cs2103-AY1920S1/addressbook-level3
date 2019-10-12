package seedu.module.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.module.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.module.testutil.ArchivedModuleBuilder;

public class ArchivedModuleListTest {

    private final ArchivedModuleList archivedModuleList = new ArchivedModuleList();
    private final ArchivedModule archivedModule = new ArchivedModuleBuilder().build();

    @Test
    public void contains_nullArchivedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedModuleList.contains(null));
    }

    @Test
    public void contains_archivedModuleNotInList_returnsFalse() {
        assertFalse(archivedModuleList.contains(archivedModule));
    }

    @Test
    public void contains_archivedModuleInList_returnsTrue() {
        archivedModuleList.add(archivedModule);
        assertTrue(archivedModuleList.contains(archivedModule));
    }

    @Test
    public void contains_moduleWithSameIdentityFieldsInList_returnsTrue() {
        archivedModuleList.add(archivedModule);
        ArchivedModule editedArchivedModule = new ArchivedModuleBuilder(archivedModule).withTitle("Different Title")
            .withDescription("The quick brown fox jumps over the lazy dog").build();
        assertTrue(archivedModuleList.contains(editedArchivedModule));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedModuleList.add(null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> archivedModuleList.asUnmodifiableObservableList().remove(0));
    }
}
