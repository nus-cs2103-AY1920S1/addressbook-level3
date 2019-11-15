package dukecooks.storage.health;

import static dukecooks.testutil.health.TypicalRecords.CALORIES_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.testutil.Assert;

public class JsonAdaptedRecordTest {
    private static final String INVALID_TYPE = "@Calories";
    private static final String INVALID_REMARK = "#no carbs";
    private static final String INVALID_VALUE = "1a";
    private static final String INVALID_TIMESTAMP = "29/02/2019";

    private static final String VALID_TYPE = CALORIES_FIRST.getType().toString();
    private static final List<JsonAdaptedRemark> VALID_REMARKS = CALORIES_FIRST.getRemarks().stream()
            .map(JsonAdaptedRemark::new)
            .collect(Collectors.toList());
    private static final String VALID_VALUE = CALORIES_FIRST.getValue().toString();
    private static final String VALID_TIMESTAMP = CALORIES_FIRST.getTimestamp().toString();

    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        JsonAdaptedRecord record = new JsonAdaptedRecord(CALORIES_FIRST);
        assertEquals(CALORIES_FIRST, record.toModelType());
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(INVALID_TYPE, VALID_VALUE, VALID_TIMESTAMP, VALID_REMARKS);
        String expectedMessage = Type.messageConstraints();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedRecord record = new JsonAdaptedRecord(null, VALID_VALUE, VALID_TIMESTAMP, VALID_REMARKS);
        String expectedMessage = String.format(JsonAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT,
                Type.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidRemarks_throwsIllegalValueException() {
        List<JsonAdaptedRemark> invalidRemarks = new ArrayList<>(VALID_REMARKS);
        invalidRemarks.add(new JsonAdaptedRemark(INVALID_REMARK));
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_TYPE, VALID_VALUE, VALID_TIMESTAMP, invalidRemarks);
        Assert.assertThrows(IllegalValueException.class, record::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_TYPE, INVALID_VALUE, VALID_TIMESTAMP, VALID_REMARKS);
        String expectedMessage = Value.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_TYPE, null, VALID_TIMESTAMP, VALID_REMARKS);
        String expectedMessage = String.format(JsonAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT, Value.class
                .getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidTimestamp_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_TYPE, VALID_VALUE, INVALID_TIMESTAMP, VALID_REMARKS);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullTimestamp_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_TYPE, VALID_VALUE, null, VALID_REMARKS);
        String expectedMessage = String.format(JsonAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class
                .getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

}
