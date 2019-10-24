package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.module.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.commons.util.JsonUtil;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;

public class JsonSerializableModuleBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModuleBookTest");
    private static final Path TYPICAL_MODULE_FILE = TEST_DATA_FOLDER.resolve("typicalModuleModuleBook.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleModuleBook.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleModuleBook.json");
    private static ArchivedModuleList archivedModules;

    @BeforeEach
    public void beforeEach() {
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        archivedModules = new ArchivedModuleList();
        archivedModules.add(archivedModule);
    }

    // TODO: Finetune the typical modulebook object and test for deadlines/links
    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULE_FILE,
                JsonSerializableModuleBook.class).get();
        ModuleBook moduleBookFromFile = dataFromFile.toModelType(archivedModules);

        ModuleBook expectedModuleBook = new ModuleBook(archivedModules);
        expectedModuleBook.addModule(new TrackedModule(new ArchivedModuleBuilder().build()));

        assertEquals(moduleBookFromFile, expectedModuleBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableModuleBook.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(archivedModules));
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableModuleBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableModuleBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModuleBook.MESSAGE_DUPLICATE_MODULES, ()
            -> dataFromFile.toModelType(archivedModules));
    }

}
