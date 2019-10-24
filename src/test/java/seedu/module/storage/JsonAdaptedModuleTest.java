package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.storage.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.module.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class JsonAdaptedModuleTest {
    private static final ArchivedModule ARCHIVED_MODULE = new ArchivedModuleBuilder().build();
    private static final ArchivedModuleList ARCHIVED_MODULE_LIST = new ArchivedModuleList();
    private static final TrackedModule TRACKED_MODULE = new TrackedModule(ARCHIVED_MODULE);

    @BeforeAll
    public static void beforeAll() {
        ARCHIVED_MODULE_LIST.add(ARCHIVED_MODULE);
    }

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(ARCHIVED_MODULE.getModuleCode(),
            new ArrayList<>(), new ArrayList<>());
        assertEquals(TRACKED_MODULE, module.toModelType(ARCHIVED_MODULE_LIST));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        String moduleCode = "CS2999";
        JsonAdaptedModule module = new JsonAdaptedModule(moduleCode, new ArrayList<>(), new ArrayList<>());
        String expectedMessage = String.format("Archived Module %s not found", moduleCode);
        assertThrows(IllegalValueException.class, expectedMessage, () -> module.toModelType(ARCHIVED_MODULE_LIST));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(null, new ArrayList<>(), new ArrayList<>());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "moduleCode");
        assertThrows(IllegalValueException.class, expectedMessage, () -> module.toModelType(ARCHIVED_MODULE_LIST));
    }
}
