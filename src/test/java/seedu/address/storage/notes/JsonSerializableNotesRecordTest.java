package seedu.address.storage.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTE;
import static seedu.address.storage.note.JsonAdaptedNote.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.note.NotesRecord;
import seedu.address.storage.note.JsonSerializableNotesRecord;
import seedu.address.testutil.note.TypicalNotes;

public class JsonSerializableNotesRecordTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonSerializableNotesRecordTest");
    private static final Path TYPICAL_NOTES_FILE = TEST_DATA_FOLDER.resolve("typicalNotesRecord.json");
    private static final Path INVALID_NOTES_FILE = TEST_DATA_FOLDER.resolve("invalidNotesRecord.json");
    private static final Path DUPLICATE_NOTES_FILE = TEST_DATA_FOLDER.resolve("duplicateNotesRecord.json");

    @Test
    public void toModelType_typicalNotesFile_success() throws Exception {
        JsonSerializableNotesRecord dataFromFile = JsonUtil.readJsonFile(TYPICAL_NOTES_FILE,
                JsonSerializableNotesRecord.class).get();
        NotesRecord notesRecordFromFile = dataFromFile.toModelType();
        NotesRecord typicalNotesRecord = TypicalNotes.getTypicalNotesRecord();
        assertEquals(notesRecordFromFile, typicalNotesRecord);
    }

    @Test
    public void toModelType_invalidNotesFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNotesRecord dataFromFile = JsonUtil.readJsonFile(INVALID_NOTES_FILE,
                JsonSerializableNotesRecord.class).get();
        assertThrows(IllegalValueException.class, MISSING_FIELD_MESSAGE_FORMAT, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateNotes_throwsIllegalValueException() throws Exception {
        JsonSerializableNotesRecord dataFromFile = JsonUtil.readJsonFile(DUPLICATE_NOTES_FILE,
                JsonSerializableNotesRecord.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_NOTE, dataFromFile::toModelType);
    }

}
