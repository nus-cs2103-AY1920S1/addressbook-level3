package dukecooks.model.health;

import static dukecooks.testutil.health.TypicalRecords.CALORIES;
import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.UniqueRecord;
import dukecooks.model.health.exceptions.DuplicateRecordException;
import dukecooks.model.health.exceptions.RecordNotFoundException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.health.RecordBuilder;

public class UniqueRecordTest {

    private final UniqueRecord uniqueRecord = new UniqueRecord();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.contains(null));
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecord.contains(GLUCOSE_FIRST));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecord.add(GLUCOSE_FIRST);
        assertTrue(uniqueRecord.contains(GLUCOSE_FIRST));
    }

    @Test
    public void contains_recordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecord.add(GLUCOSE_FIRST);
        Record editedAlice = new RecordBuilder(GLUCOSE_FIRST).withRemarks(CommandTestUtil.VALID_REMARK_CALORIES)
                .build();
        assertTrue(uniqueRecord.contains(editedAlice));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.add(null));
    }

    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecord.add(GLUCOSE_FIRST);
        Assert.assertThrows(DuplicateRecordException.class, () -> uniqueRecord.add(GLUCOSE_FIRST));
    }

    @Test
    public void setRecord_nullTargetRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.setRecord(null, GLUCOSE_FIRST));
    }

    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.setRecord(GLUCOSE_FIRST, null));
    }

    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        Assert.assertThrows(RecordNotFoundException.class, () -> uniqueRecord.setRecord(GLUCOSE_FIRST, GLUCOSE_FIRST));
    }

    @Test
    public void setRecord_editedRecordIsSameRecord_success() {
        uniqueRecord.add(GLUCOSE_FIRST);
        uniqueRecord.setRecord(GLUCOSE_FIRST, GLUCOSE_FIRST);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        expectedUniqueRecord.add(GLUCOSE_FIRST);
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecord_editedRecordHasSameIdentity_success() {
        uniqueRecord.add(GLUCOSE_FIRST);
        Record editedAlice = new RecordBuilder(GLUCOSE_FIRST).withRemarks(CommandTestUtil.VALID_REMARK_CALORIES)
                .build();
        uniqueRecord.setRecord(GLUCOSE_FIRST, editedAlice);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        expectedUniqueRecord.add(editedAlice);
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecord_editedRecordHasDifferentIdentity_success() {
        uniqueRecord.add(GLUCOSE_FIRST);
        uniqueRecord.setRecord(GLUCOSE_FIRST, CALORIES);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        expectedUniqueRecord.add(CALORIES);
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecord_editedRecordHasNonUniqueIdentity_throwsDuplicateRecordException() {
        uniqueRecord.add(GLUCOSE_FIRST);
        uniqueRecord.add(CALORIES);
        Assert.assertThrows(DuplicateRecordException.class, () -> uniqueRecord.setRecord(GLUCOSE_FIRST, CALORIES));
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.remove(null));
    }

    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        Assert.assertThrows(RecordNotFoundException.class, () -> uniqueRecord.remove(GLUCOSE_FIRST));
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecord.add(GLUCOSE_FIRST);
        uniqueRecord.remove(GLUCOSE_FIRST);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecords_nullUniqueRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.setRecords((UniqueRecord) null));
    }

    @Test
    public void setRecords_uniqueRecord_replacesOwnListWithProvidedUniqueRecord() {
        uniqueRecord.add(GLUCOSE_FIRST);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        expectedUniqueRecord.add(CALORIES);
        uniqueRecord.setRecords(expectedUniqueRecord);
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecord.setRecords((List<Record>) null));
    }

    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecord.add(GLUCOSE_FIRST);
        List<Record> record = Collections.singletonList(CALORIES);
        uniqueRecord.setRecords(record);
        UniqueRecord expectedUniqueRecord = new UniqueRecord();
        expectedUniqueRecord.add(CALORIES);
        assertEquals(expectedUniqueRecord, uniqueRecord);
    }

    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<Record> listWithDuplicateRecords = Arrays.asList(GLUCOSE_FIRST, GLUCOSE_FIRST);
        Assert.assertThrows(DuplicateRecordException.class, ()
            -> uniqueRecord.setRecords(listWithDuplicateRecords));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRecord.asUnmodifiableObservableList().remove(0));
    }
}
