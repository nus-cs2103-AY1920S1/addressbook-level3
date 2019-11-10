package seedu.moolah.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.commons.util.JsonUtil;
import seedu.moolah.model.MooLah;
import seedu.moolah.testutil.TypicalMooLah;

public class JsonSerializableMooLahTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMooLahTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesMooLah.json");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseMooLah.json");
    private static final Path DUPLICATE_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("duplicateExpenseMooLah.json");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventsMooLah.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventMooLah.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventMooLah.json");

//    @Test
//    public void toModelType_typicalExpensesFile_success() throws Exception {
//        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXPENSES_FILE,
//                JsonSerializableMooLah.class).get();
//        MooLah mooLahFromFile = dataFromFile.toModelType();
//        MooLah typicalExpensesMooLah = TypicalMooLah.getTypicalExpensesOnlyMooLah();
//        assertEquals(mooLahFromFile, typicalExpensesMooLah);
//    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(INVALID_EXPENSE_FILE,
                JsonSerializableMooLah.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXPENSE_FILE,
                JsonSerializableMooLah.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMooLah.MESSAGE_DUPLICATE_EXPENSE,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTS_FILE,
                JsonSerializableMooLah.class).get();
        MooLah mooLahFromFile = dataFromFile.toModelType();
        MooLah typicalEventsMooLah = TypicalMooLah.getTypicalEventsOnlyMooLah();
        assertEquals(mooLahFromFile, typicalEventsMooLah);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                JsonSerializableMooLah.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableMooLah dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENT_FILE,
                JsonSerializableMooLah.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMooLah.MESSAGE_DUPLICATE_EVENT,
                dataFromFile::toModelType);
    }
}

