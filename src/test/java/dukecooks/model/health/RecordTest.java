package dukecooks.model.health;

import static dukecooks.testutil.health.TypicalRecords.CALORIES;
import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.testutil.Assert;
import dukecooks.testutil.health.RecordBuilder;

public class RecordTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Record record = new RecordBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> record.getRemarks().remove(0));
    }

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(GLUCOSE_FIRST.isSameRecord(GLUCOSE_FIRST));

        // null -> returns false
        assertFalse(GLUCOSE_FIRST.isSameRecord(null));

        // different type -> returns false
        Record editedRecord = new RecordBuilder(GLUCOSE_FIRST).withType(CommandTestUtil.VALID_TYPE_CALORIES).build();
        assertFalse(GLUCOSE_FIRST.isSameRecord(editedRecord));

        // same type, different attributes -> returns false
        editedRecord = new RecordBuilder(GLUCOSE_FIRST)
                .withRemarks(CommandTestUtil.VALID_REMARK_CALORIES).build();
        assertTrue(GLUCOSE_FIRST.isSameRecord(editedRecord));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Record aliceCopy = new RecordBuilder(GLUCOSE_FIRST).build();
        assertTrue(GLUCOSE_FIRST.equals(aliceCopy));

        // same object -> returns true
        assertTrue(GLUCOSE_FIRST.equals(GLUCOSE_FIRST));

        // null -> returns false
        assertFalse(GLUCOSE_FIRST.equals(null));

        // different type -> returns false
        assertFalse(GLUCOSE_FIRST.equals(5));

        // different record -> returns false
        assertFalse(GLUCOSE_FIRST.equals(CALORIES));

        // different type -> returns false
        Record editedAlice = new RecordBuilder(GLUCOSE_FIRST).withType(CommandTestUtil.VALID_TYPE_CALORIES).build();
        assertFalse(GLUCOSE_FIRST.equals(editedAlice));

        // different remarks -> returns true
        editedAlice = new RecordBuilder(GLUCOSE_FIRST).withRemarks(CommandTestUtil.VALID_REMARK_CALORIES).build();
        assertTrue(GLUCOSE_FIRST.equals(editedAlice));
    }

    @Test
    public void testRecordHashCode() {
        Type type = Type.valueOf("Glucose");
        Remark remark = new Remark("B");
        Set<Remark> remarks = new HashSet<>();
        Value value = new Value("1");
        Timestamp timestamp = new Timestamp("12/09/2019 12:00");
        remarks.add(remark);
        Record record1 = new Record(type, value, timestamp, remarks);
        Record record2 = new Record(type, value, timestamp, remarks);
        assertEquals(record1.hashCode(), record2.hashCode());
    }
}
