package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class JsonAdaptedArchivedModuleTest {
    private static final ArchivedModule VALID_ARCHIVED_MODULE = new ArchivedModuleBuilder().build();

    private static final String VALID_MODULECODE = VALID_ARCHIVED_MODULE.getModuleCode();
    private static final String VALID_TITLE = VALID_ARCHIVED_MODULE.getTitle();
    private static final String VALID_DESCRIPTION = VALID_ARCHIVED_MODULE.getDescription();


    @Test
    public void toModelType_validModule() throws Exception {
        JsonAdaptedArchivedModule jsonArchivedModule = new JsonAdaptedArchivedModule(
            VALID_MODULECODE, VALID_TITLE, VALID_DESCRIPTION, null, null, new ArrayList<>());

        assertEquals(VALID_ARCHIVED_MODULE, jsonArchivedModule.toModelType());
    }
}
