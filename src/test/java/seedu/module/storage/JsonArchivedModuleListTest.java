package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.storage.JsonArchivedModuleList.readArchivedModules;

import org.junit.jupiter.api.Test;

import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;


class JsonArchivedModuleListTest {
    private static final String VALID_ARCHIVED_MODULES_FILE_PATH = "data/archivedModules.json";
    private static final String INVALID_ARCHIVED_MODULES_FILE_PATH = "data/doesntexist.json";
    // To trigger URISyntaxException
    private static final String INVALID_ARCHIVED_MODULES_FILE_PATH_FORMAT = "data/archivedModules.json:";

    @Test
    public void readArchivedModules_validArchivedModulesFilePath_returnArchivedModules() {
        ArchivedModuleList jsonArchivedModuleList = readArchivedModules(VALID_ARCHIVED_MODULES_FILE_PATH);
        ArchivedModuleList expectedArchivedModuleList = new ArchivedModuleList();

        expectedArchivedModuleList.add(new ArchivedModule("CS2103T", "Software Engineering", "Lorem Ipsum"));

        assertEquals(jsonArchivedModuleList, expectedArchivedModuleList);
    }

    @Test
    public void readArchivedModules_invalidArchivedModulesFilePath_returnEmptyArchivedModules() {
        ArchivedModuleList jsonArchivedModuleList = readArchivedModules(INVALID_ARCHIVED_MODULES_FILE_PATH);
        ArchivedModuleList expectedArchivedModuleList = new ArchivedModuleList();

        assertEquals(jsonArchivedModuleList, expectedArchivedModuleList);
    }

    @Test
    public void readArchivedModules_invalidArchivedModulesFilePathFormat_returnEmptyArchivedModules() {
        ArchivedModuleList jsonArchivedModuleList = readArchivedModules(INVALID_ARCHIVED_MODULES_FILE_PATH_FORMAT);
        ArchivedModuleList expectedArchivedModuleList = new ArchivedModuleList();

        assertEquals(jsonArchivedModuleList, expectedArchivedModuleList);
    }
}
