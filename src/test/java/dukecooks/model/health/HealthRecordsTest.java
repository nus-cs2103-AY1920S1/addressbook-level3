package dukecooks.model.health;

import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_FIRST;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.exceptions.DuplicateRecordException;
import dukecooks.model.health.exceptions.RecordNotFoundException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.health.RecordBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HealthRecordsTest {

    private final HealthRecords healthRecords = new HealthRecords();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), healthRecords.getHealthRecordsList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> healthRecords.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHealthRecords_replacesData() {
        HealthRecords newData = getTypicalHealthRecords();
        healthRecords.resetData(newData);
        assertEquals(newData, healthRecords);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same identity fields
        Record editedGlucoseFirst = new RecordBuilder(GLUCOSE_FIRST)
                .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE)
                .build();
        List<Record> newRecords = Arrays.asList(GLUCOSE_FIRST, editedGlucoseFirst);
        HealthRecordsStub newData = new HealthRecordsStub(newRecords);

        Assert.assertThrows(DuplicateRecordException.class, () -> healthRecords.resetData(newData));
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> healthRecords.hasRecord(null));
    }

    @Test
    public void hasRecord_recordNotInHealthRecords_returnsFalse() {
        assertFalse(healthRecords.hasRecord(GLUCOSE_FIRST));
    }

    @Test
    public void hasRecord_recordInHealthRecords_returnsTrue() {
        healthRecords.addRecord(GLUCOSE_FIRST);
        assertTrue(healthRecords.hasRecord(GLUCOSE_FIRST));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInHealthRecords_returnsTrue() {
        healthRecords.addRecord(GLUCOSE_FIRST);
        Record editedGlucoseFirst = new RecordBuilder(GLUCOSE_FIRST)
                .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE)
                .build();
        assertTrue(healthRecords.hasRecord(editedGlucoseFirst));
    }

    @Test
    public void retrieveRecord_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> healthRecords.retrieveRecord(null));
    }

    @Test
    public void retrieveRecord_recordNotInHealthRecords_throwsRecordNotFoundException() {
        Assert.assertThrows(RecordNotFoundException.class, () -> healthRecords.retrieveRecord(GLUCOSE_FIRST));
    }

    @Test
    public void retrieveRecord_recordInHealthRecords_success() {
        healthRecords.addRecord(GLUCOSE_FIRST);
        assertEquals(GLUCOSE_FIRST, healthRecords.retrieveRecord(GLUCOSE_FIRST));
    }

    @Test
    public void retrieveRecord_recordWithSameIdentityFieldsInHealthRecords_returnsTrue() {
        healthRecords.addRecord(GLUCOSE_FIRST);
        Record editedGlucoseFirst = new RecordBuilder(GLUCOSE_FIRST)
                .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE)
                .build();
        assertEquals(GLUCOSE_FIRST, healthRecords.retrieveRecord(editedGlucoseFirst));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> healthRecords
                .getHealthRecordsList().remove(0));
    }

    /**
     * A stub ReadOnlyHealthRecords whose records list can violate interface constraints.
     */
    private static class HealthRecordsStub implements ReadOnlyHealthRecords {
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        HealthRecordsStub(Collection<Record> records) {
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Record> getHealthRecordsList() {
            return records;
        }
    }

}
