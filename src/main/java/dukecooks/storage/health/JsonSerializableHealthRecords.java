package dukecooks.storage.health;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.health.components.Record;

/**
 * An Immutable Health Records that is serializable to JSON format.
 */
@JsonRootName(value = "healthrecords")
class JsonSerializableHealthRecords {

    public static final String MESSAGE_DUPLICATE_RECORD = "Record List contains duplicate record(s).";

    private final List<JsonAdaptedRecord> healthrecords = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserProfile} with the given healthrecords.
     */
    @JsonCreator
    public JsonSerializableHealthRecords(@JsonProperty("healthrecords") List<JsonAdaptedRecord> person) {
        this.healthrecords.addAll(person);
    }

    /**
    * Converts a given {@code ReadOnlyUserProfile} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
    */
    public JsonSerializableHealthRecords(ReadOnlyHealthRecords source) {
        healthrecords.addAll(source.getHealthRecordsList().stream().map(JsonAdaptedRecord::new)
                .collect(Collectors.toList()));
    }

    /**
    *  Converts this Health Records into the model's {@code HealthRecords} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public HealthRecords toModelType() throws IllegalValueException {
        HealthRecords healthRecords = new HealthRecords();
        for (JsonAdaptedRecord jsonAdaptedRecord : healthrecords) {
            Record record = jsonAdaptedRecord.toModelType();
            if (healthRecords.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            healthRecords.addRecord(record);
        }
        return healthRecords;
    }
}
