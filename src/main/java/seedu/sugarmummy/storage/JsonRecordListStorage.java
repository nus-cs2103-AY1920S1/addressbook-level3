package seedu.sugarmummy.storage;

import java.nio.file.Path;

import seedu.sugarmummy.model.record.UniqueRecordList;

/**
 * Represents the specific version {@code JsonGeneralStorage} about record list.
 */
public class JsonRecordListStorage extends JsonGeneralStorage<UniqueRecordList, JsonSerializableRecordList> {

    public JsonRecordListStorage(Path filePath) {
        super(filePath, UniqueRecordList.class, JsonSerializableRecordList.class);
    }
}
