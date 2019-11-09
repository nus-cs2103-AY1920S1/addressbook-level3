package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.health.components.Record;
import dukecooks.testutil.Assert;
import dukecooks.testutil.health.RecordBuilder;

public class AddRecordCommandTest {

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddRecordCommand(null));
    }

    @Test
    public void execute_recordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();
        Record validRecord = new RecordBuilder().build();

        CommandResult commandResult = new AddRecordCommand(validRecord).execute(modelStub);

        assertEquals(String.format(AddRecordCommand.MESSAGE_SUCCESS, validRecord), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        Record validRecord = new RecordBuilder().build();
        AddRecordCommand addRecordCommand = new AddRecordCommand(validRecord);
        ModelStub modelStub = new ModelStubWithRecord(validRecord);

        Assert.assertThrows(CommandException.class, AddRecordCommand.MESSAGE_DUPLICATE_RECORD, () -> addRecordCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        Record alice = new RecordBuilder().withType("Glucose").build();
        Record bob = new RecordBuilder().withType("Calories").build();
        AddRecordCommand addAliceCommand = new AddRecordCommand(alice);
        AddRecordCommand addBobCommand = new AddRecordCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddRecordCommand addAliceCommandCopy = new AddRecordCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different record -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single record.
     */
    private class ModelStubWithRecord extends ModelStub {
        private final Record record;

        ModelStubWithRecord(Record record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {
        final ArrayList<Record> recordsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(Record record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(Record record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public ReadOnlyHealthRecords getHealthRecords() {
            return new HealthRecords();
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            requireNonNull(predicate);
            recordsAdded.stream().filter(predicate).collect(Collectors.toList());
        }
    }

}
