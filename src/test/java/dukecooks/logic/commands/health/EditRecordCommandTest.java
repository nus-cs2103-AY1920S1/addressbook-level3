package dukecooks.logic.commands.health;

import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.components.Record;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.health.EditRecordDescriptorBuilder;
import dukecooks.testutil.health.RecordBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditRecordCommand.
 */
public class EditRecordCommandTest {

    private Model model = new ModelManager(getTypicalHealthRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Record editedRecord = new RecordBuilder().build();

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new HealthRecords(model.getHealthRecords()), new UserPrefs());
        expectedModel.setRecord(model.getFilteredRecordList().get(0), editedRecord);
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(editedRecord.getType()));

        EditRecordCommand.EditRecordDescriptor descriptor =
                new EditRecordDescriptorBuilder(model.getFilteredRecordList().get(0), editedRecord).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD, descriptor);

        CommandTestUtil.assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRecord = Index.fromOneBased(model.getFilteredRecordList().size());
        Record lastRecord = model.getFilteredRecordList().get(indexLastRecord.getZeroBased());

        RecordBuilder recordInList = new RecordBuilder(lastRecord);
        Record editedRecord = recordInList.withType(CommandTestUtil.VALID_TYPE_GLUCOSE)
                .withTimestamp(CommandTestUtil.VALID_TIMESTAMP_CALORIES)
                .withRemarks(CommandTestUtil.VALID_REMARK_GLUCOSE).build();

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withType(CommandTestUtil.VALID_TYPE_GLUCOSE)
                .withTimestamp(CommandTestUtil.VALID_TIMESTAMP_CALORIES)
                .withRemarksToAdd(CommandTestUtil.VALID_REMARK_GLUCOSE)
                .withRemarksToRemove(lastRecord.getRemarks().stream().map(i
                    -> String.valueOf(i).replace("[", "").replace("]", ""))
                        .collect(Collectors.toList()).toArray(new String[lastRecord.getRemarks().size()]))
                        .build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(indexLastRecord, descriptor);

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new HealthRecords(model.getHealthRecords()), new UserPrefs());
        expectedModel.setRecord(lastRecord, editedRecord);
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(editedRecord.getType()));

        CommandTestUtil.assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditRecordCommand editRecordCommand = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                new EditRecordCommand.EditRecordDescriptor());
        Record editedRecord = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased());

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new HealthRecords(model.getHealthRecords()), new UserPrefs());
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(editedRecord.getType()));

        CommandTestUtil.assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Record recordInFilteredList = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD
                .getZeroBased());
        Record editedRecord = new RecordBuilder(recordInFilteredList).withType(CommandTestUtil.VALID_TYPE_GLUCOSE)
                .build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                new EditRecordDescriptorBuilder().withType(CommandTestUtil.VALID_TYPE_GLUCOSE).build());

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new HealthRecords(model.getHealthRecords()), new UserPrefs());
        expectedModel.setRecord(model.getFilteredRecordList().get(0), editedRecord);
        expectedModel.updateFilteredRecordList(x -> x.getType().equals(editedRecord.getType()));

        CommandTestUtil.assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRecordUnfilteredList_failure() {
        Record firstRecord = model.getFilteredRecordList().get(TypicalIndexes.INDEX_FIRST_RECORD.getZeroBased());
        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(firstRecord).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(TypicalIndexes.INDEX_SECOND_RECORD, descriptor);

        CommandTestUtil.assertRecordCommandFailure(editRecordCommand, model,
                EditRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_duplicateRecordFilteredList_failure() {
        // edit record in filtered list into a duplicate in HealthRecords
        Record recordInList = model.getHealthRecords().getHealthRecordsList().get(TypicalIndexes.INDEX_SECOND_RECORD
                .getZeroBased());
        EditRecordCommand editRecordCommand = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                new EditRecordDescriptorBuilder(recordInList).build());

        CommandTestUtil.assertRecordCommandFailure(editRecordCommand, model,
                EditRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_invalidRecordIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withType(CommandTestUtil.VALID_TYPE_GLUCOSE).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertRecordCommandFailure(editRecordCommand, model,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditRecordCommand standardCommand = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                CommandTestUtil.DESC_CALORIES);

        // same values -> returns true
        EditRecordCommand.EditRecordDescriptor copyDescriptor = new EditRecordCommand
                .EditRecordDescriptor(CommandTestUtil.DESC_CALORIES);
        EditRecordCommand commandWithSameValues = new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditRecordCommand(TypicalIndexes.INDEX_SECOND_RECORD,
                CommandTestUtil.DESC_CALORIES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditRecordCommand(TypicalIndexes.INDEX_FIRST_RECORD,
                CommandTestUtil.DESC_GLUCOSE)));
    }

}
