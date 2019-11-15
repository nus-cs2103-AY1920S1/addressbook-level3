package dukecooks.testutil.health;

import java.util.HashSet;
import java.util.Set;

import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.model.util.SampleRecordDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_TYPE = "Glucose";
    public static final String DEFAULT_REMARK = "after meal";
    public static final String DEFAULT_VALUE = "20";
    public static final String DEFAULT_TIMESTAMP = "12/10/2019 12:00";

    private Type type;
    private Set<Remark> remarks;
    private Value value;
    private Timestamp timestamp;

    public RecordBuilder() {
        type = Type.valueOf(DEFAULT_TYPE);
        value = new Value(DEFAULT_VALUE);
        timestamp = new Timestamp(DEFAULT_TIMESTAMP);
        remarks = new HashSet<>();
        remarks.add(new Remark(DEFAULT_REMARK));
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     */
    public RecordBuilder(Record recordToCopy) {
        type = recordToCopy.getType();
        value = recordToCopy.getValue();
        timestamp = recordToCopy.getTimestamp();
        remarks = new HashSet<>(recordToCopy.getRemarks());
    }

    /**
     * Sets the {@code Type} of the {@code Record} that we are building.
     */
    public RecordBuilder withType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    /**
     * Parses the {@code remarks} into a {@code Set<Remark>} and set it
     * to the {@code Record} that we are building.
     */
    public RecordBuilder withRemarks(String ... remarks) {
        this.remarks = SampleRecordDataUtil.getRemarkSet(remarks);
        return this;
    }

    /**
     * Sets the {@code Value} of the {@code Record} that we are building.
     */
    public RecordBuilder withValue(String value) {
        this.value = new Value(value);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Record} that we are building.
     */
    public RecordBuilder withTimestamp(String timestamp) {
        this.timestamp = new Timestamp(timestamp);
        return this;
    }

    public Record build() {
        return new Record(type, value, timestamp, remarks);
    }

}
