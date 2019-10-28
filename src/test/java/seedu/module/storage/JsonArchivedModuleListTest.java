package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.module.storage.JsonArchivedModuleList.readArchivedModules;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModuleList;
import seedu.module.testutil.ArchivedModuleBuilder;

class JsonArchivedModuleListTest {
    private static final String VALID_ARCHIVED_MODULES_FILE_PATH = "data/archivedModules.json";
    private static final String INVALID_ARCHIVED_MODULES_FILE_PATH = "data/doesntexist.json";

    @Test
    public void readArchivedModules_validArchivedModulesFilePath_returnArchivedModules() {
        ArchivedModuleList jsonArchivedModuleList = readArchivedModules(VALID_ARCHIVED_MODULES_FILE_PATH);
        ArchivedModuleList expectedArchivedModuleList = new ArchivedModuleList();

        expectedArchivedModuleList.add(new ArchivedModuleBuilder().build());

        assertEquals(jsonArchivedModuleList, expectedArchivedModuleList);
    }

    @Test
    public void readArchivedModules_invalidArchivedModulesFilePath_throwsNullPointerException() {
        assertThrows(java.lang.NullPointerException.class, ()
            -> readArchivedModules(INVALID_ARCHIVED_MODULES_FILE_PATH));
    }
}
