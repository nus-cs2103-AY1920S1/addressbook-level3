package dukecooks.logic.commands.health;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DATETIME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Remark;
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

    private static Event event;

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

        Type type = editedRecord.getType();
        double value = editedRecord.getValue().value;
        if (!type.isValidNumber(type.toString(), value)) {
            throw new CommandException(type.messageInflatedValue());
        }

        model.setRecord(recordToEdit, editedRecord);

        model.updateFilteredRecordList(x -> x.getType().equals(type));
        if (type.equals(Type.Weight) || type.equals(Type.Height)) {
            LinkProfile.updateProfile(model, type);
        }
        // trigger event to direct user to view the output
        event = Event.getInstance();
        event.set("health", "type");

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

        Set<Remark> updatedRemarks;
        if (editRecordDescriptor.getRemarksToAdd().isPresent()
                || editRecordDescriptor.getRemarksToRemove().isPresent()) {

            updatedRemarks = new HashSet<>(recordToEdit.getRemarks());

            if (editRecordDescriptor.getRemarksToAdd().isPresent()) {
                updatedRemarks.addAll(editRecordDescriptor.getRemarksToAdd().get());
            }

            if (editRecordDescriptor.getRemarksToRemove().isPresent()) {
                updatedRemarks.removeAll(editRecordDescriptor.getRemarksToRemove().get());
            }

        } else {
            updatedRemarks = recordToEdit.getRemarks();
        }

        return new Record(updatedType, updatedValue, updatedTimestamp, updatedRemarks);
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
        private Set<Remark> remarksToAdd;
        private Set<Remark> remarksToRemove;

        public EditRecordDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setType(toCopy.type);
            setValue(toCopy.value);
            setTimestamp(toCopy.timestamp);
            setAddRemarks(toCopy.remarksToAdd);
            setRemoveRemarks(toCopy.remarksToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(type, value, timestamp, remarksToAdd, remarksToRemove);
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

        /**
         * Set {@code remarks} to this object's {@code remarksToAdd}.
         * A defensive copy of {@code remarksToAdd} is used internally.
         */
        public void setAddRemarks(Set<Remark> remarks) {
            this.remarksToAdd = (remarks != null) ? new HashSet<>(remarks) : null;
        }

        /**
         * Set {@code remarks} to this object's {@code remarksToRemove}.
         * A defensive copy of {@code remarksToRemove} is used internally.
         */
        public void setRemoveRemarks(Set<Remark> remarks) {
            this.remarksToRemove = (remarks != null) ? new HashSet<>(remarks) : null;
        }

        /**
         * Adds {@code remarks} to this object's {@code remarksToAdd}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public void addRemarks(Set<Remark> remarks) {
            this.remarksToAdd = (remarks != null) ? new HashSet<>(remarks) : null;
        }

        /**
         * Removes {@code remarks} to this object's {@code remarks}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public void removeRemarks(Set<Remark> remarks) {
            this.remarksToRemove = (remarks != null) ? new HashSet<>(remarks) : null;
        }

        /**
         * Returns an unmodifiable remark set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code remarks} is null.
         */
        public Optional<Set<Remark>> getRemarksToAdd() {
            return (remarksToAdd != null)
                    ? Optional.of(Collections.unmodifiableSet(remarksToAdd))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable remark set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code remarks} is null.
         */
        public Optional<Set<Remark>> getRemarksToRemove() {
            return (remarksToRemove != null)
                    ? Optional.of(Collections.unmodifiableSet(remarksToRemove))
                    : Optional.empty();
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
