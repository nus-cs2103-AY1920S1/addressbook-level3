package seedu.module.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.module.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ModuleBookBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class ModuleBookTest {

    private ModuleBook moduleBook;

    @BeforeEach
    public void beforeEach() {
        moduleBook = new ModuleBookBuilder().build();
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), moduleBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModuleBook_replacesData() {
        ModuleBook newData = new ModuleBook();
        moduleBook.resetData(newData);
        assertEquals(newData, moduleBook);
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleBook.hasModule(null));
    }

    @Test
    public void hasTrackedModule_trackedModuleNotInModuleBook_returnsFalse() {
        assertFalse(moduleBook.hasModule(new TrackedModuleBuilder().build()));
    }

    @Test
    public void hasTrackedModule_trackedModuleInModuleBook_returnsTrue() {
        TrackedModule trackedModule = new TrackedModuleBuilder()
            .withModule(new ArchivedModuleBuilder().build())
            .build();
        moduleBook.addModule(trackedModule);
        assertTrue(moduleBook.hasModule(trackedModule));
    }

    @Test
    public void getTrackedModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> moduleBook.getModuleList().remove(0));
    }
}
