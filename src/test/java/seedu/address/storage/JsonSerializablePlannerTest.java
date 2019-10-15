package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Planner;
import seedu.address.testutil.TypicalContacts;

public class JsonSerializablePlannerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePlannerTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalContactsPlanner.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve("invalidContactPlanner.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve("duplicateContactPlanner.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializablePlanner.class).get();
        Planner plannerFromFile = dataFromFile.toModelType();
        Planner typicalContactsPlanner = TypicalContacts.getTypicalPlanner();
        assertEquals(plannerFromFile, typicalContactsPlanner);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializablePlanner.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializablePlanner.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePlanner.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

}
