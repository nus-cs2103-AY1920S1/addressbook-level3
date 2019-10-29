package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;

/**
 * Represents an Immutable RecordList that is serializable to JSON format.
 */
@JsonRootName(value = "recordlist")
public class JsonSerializableRecordList implements JsonSerializableContent<UniqueRecordList> {

    public static final String MESSAGE_DUPLICATE_RECORD = "Record list contains duplicate record(s).";

    private final List<JsonAdaptedRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRecordList} with the given records.
     */
    @JsonCreator
    public JsonSerializableRecordList(@JsonProperty("records") List<JsonAdaptedRecord> records) {
        this.records.addAll(records);
    }

    /**
     * Converts a given {@code UniqueRecordList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecordList}.
     */
    public JsonSerializableRecordList(UniqueRecordList source) {
        records.addAll(source.asUnmodifiableObservableList().stream().map(JsonAdaptedRecord::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this record list into the sugarmummy.recmfood.model's {@code UniqueRecordList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    @Override
    public UniqueRecordList toModelType() throws IllegalValueException {
        UniqueRecordList recordList = new UniqueRecordList();
        for (JsonAdaptedRecord jsonAdaptedRecord : records) {
            Record record = jsonAdaptedRecord.toModelType();
            if (recordList.contains(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            recordList.add(record);
        }
        return recordList;
    }

}
