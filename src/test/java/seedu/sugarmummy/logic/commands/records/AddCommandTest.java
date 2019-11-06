package seedu.sugarmummy.logic.commands.records;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.time.DateTime;

public class AddCommandTest {

    private LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
    private LocalTime lt = LocalTime.of(8, 0, 0);
    private DateTime dt = new DateTime(ld, lt);
    private BloodSugar bs = new BloodSugar(new Concentration("12.34"), dt);
    private Bmi bmi = new Bmi(new Height("2.34"), new Weight("23.34"), dt);

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_recordAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();

        CommandResult commandResult = new AddCommand(bs).execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, bs), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(bs), modelStub.recordsAdded);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        //        Person validPerson = new PersonBuilder().build();

        AddCommand addCommand = new AddCommand(bs);
        ModelStub modelStub = new ModelStubWithRecord(bs);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_RECORD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddCommand addBloodSugarCommand = new AddCommand(bs);
        AddCommand addBmiCommand = new AddCommand(bmi);

        // same object -> returns true
        assertTrue(addBloodSugarCommand.equals(addBloodSugarCommand));

        // same values -> returns true
        AddCommand addBloodSugarCommandCopy = new AddCommand(bs);
        assertTrue(addBloodSugarCommand.equals(addBloodSugarCommandCopy));

        // different types -> returns false
        assertFalse(addBloodSugarCommand.equals(1));

        // null -> returns false
        assertFalse(addBloodSugarCommand.equals(null));

        // different record -> returns false
        assertFalse(addBloodSugarCommand.equals(addBmiCommand));
    }

    /**
     * A Model stub that always accept the person being added.
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
    }

    /**
     * A Model stub that contains a single person.
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

}
