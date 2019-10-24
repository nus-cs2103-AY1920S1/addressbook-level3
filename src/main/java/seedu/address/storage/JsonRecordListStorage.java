package seedu.address.storage;

import java.nio.file.Path;

import seedu.address.model.record.UniqueRecordList;

/**
 * Represents the specific version {@code JsonGeneralStorage} about record list.
 */
public class JsonRecordListStorage extends JsonGeneralStorage<UniqueRecordList, JsonSerializableRecordList> {

    public JsonRecordListStorage(Path filePath) {
        super(filePath, UniqueRecordList.class, JsonSerializableRecordList.class);
    }
}
