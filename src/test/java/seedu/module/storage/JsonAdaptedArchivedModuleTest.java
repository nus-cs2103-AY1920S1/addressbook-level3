package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;

public class JsonAdaptedArchivedModuleTest {
    private static final String VALID_MODULECODE = "CS2103T";
    private static final String VALID_TITLE = "Software Engineering";
    private static final String VALID_DESCRIPTION = "Lorem Ipsum";

    private static final ArchivedModule VALID_ARCHIVED_MODULE = new ArchivedModule(
        VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION);

    @Test
    public void toModelType_validModule() throws Exception {
        JsonAdaptedArchivedModule jsonArchivedModule = new JsonAdaptedArchivedModule(
            VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION);

        assertEquals(VALID_ARCHIVED_MODULE, jsonArchivedModule.toModelType());
    }
}
