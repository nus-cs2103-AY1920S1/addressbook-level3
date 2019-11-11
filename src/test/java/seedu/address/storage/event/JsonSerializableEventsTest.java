package seedu.address.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.storage.event.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.event.EventRecord;
import seedu.address.testutil.event.TypicalEvents;

public class JsonSerializableEventsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonSerializableEventsTest");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventRecord.json");
    private static final Path INVALID_EVENTS_FILE = TEST_DATA_FOLDER.resolve("invalidEventRecord.json");

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        JsonSerializableEvents dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTS_FILE,
                JsonSerializableEvents.class).get();
        EventRecord eventRecordFromFile = dataFromFile.toModelType();
        EventRecord typicalEventRecord = TypicalEvents.getTypicalEventsRecord();
        assertEquals(eventRecordFromFile, typicalEventRecord);
    }

    @Test
    public void toModelType_invalidEventsFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEvents dataFromFile = JsonUtil.readJsonFile(INVALID_EVENTS_FILE,
                JsonSerializableEvents.class).get();
        assertThrows(IllegalValueException.class, String.format(MISSING_FIELD_MESSAGE_FORMAT, "RECURRENCE TYPE"),
                dataFromFile::toModelType);
    }
}
