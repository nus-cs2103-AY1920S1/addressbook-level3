package dukecooks.logic.commands.health;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DATETIME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;

/**
 * Edits the details of an existing record in Duke Cooks.
 */
public class EditRecordCommand extends EditCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TYPE + "TYPE] "
            + "[" + PREFIX_DATETIME + "TIMESTAMP]... "
            + "[" + PREFIX_VALUE + "VALUE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_VALUE + "123";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Edited Record: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the Duke Cooks.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index of the record in the filtered record list to edit
     * @param editRecordDescriptor details to edit the record with
     */
    public EditRecordCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = new EditRecordDescriptor(editRecordDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

        if (!recordToEdit.isSameRecord(editedRecord) && model.hasRecord(editedRecord)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.setRecord(recordToEdit, editedRecord);
        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedRecord));
    }

    /**
     * Creates and returns a {@code Record} with the details of {@code recordToEdit}
     * edited with {@code editRecordDescriptor}.
     */
    private static Record createEditedRecord(Record recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        Type updatedType = editRecordDescriptor.getType().orElse(recordToEdit.getType());
        Value updatedValue = editRecordDescriptor.getValue().orElse(recordToEdit.getValue());
        Timestamp updatedTimestamp = editRecordDescriptor.getTimestamp().orElse(recordToEdit.getTimestamp());

        return new Record(updatedType, updatedValue, updatedTimestamp);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecordCommand)) {
            return false;
        }

        // state check
        EditRecordCommand e = (EditRecordCommand) other;
        return index.equals(e.index)
                && editRecordDescriptor.equals(e.editRecordDescriptor);
    }

    /**
     * Stores the details to edit the record with. Each non-empty field value will replace the
     * corresponding field value of the record.
     */
    public static class EditRecordDescriptor {
        private Type type;
        private Value value;
        private Timestamp timestamp;

        public EditRecordDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setType(toCopy.type);
            setValue(toCopy.value);
            setTimestamp(toCopy.timestamp);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(type, value, timestamp);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        public Optional<Value> getValue() {
            return Optional.ofNullable(value);
        }

        public Optional<Timestamp> getTimestamp() {
            return Optional.ofNullable(timestamp);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }

            // state check
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getType().equals(e.getType())
                    && getTimestamp().equals(e.getTimestamp())
                    && getValue().equals(e.getValue());
        }
    }
}
