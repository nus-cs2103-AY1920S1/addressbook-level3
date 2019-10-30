package seedu.address.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKER_TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Description;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number used in the displayed Expenses list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "REMINDER_MESSAGE"
            + "[" + PREFIX_INDEX + "CONDITION INDEX]..."
            + PREFIX_TRACKER_TYPE + "(Optional) TRACKER TYPE"
            + PREFIX_AMOUNT + "(Optional) Quota"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Don't be broke. "
            + PREFIX_INDEX + "1 "
            + PREFIX_TRACKER_TYPE + "AMOUNT"
            + PREFIX_AMOUNT + "100 \n";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the address book.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the expenseReminder in the filtered expense reminder list to edit
     * @param editReminderDescriptor details to edit the person with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Reminder> lastShownList = model.getFilteredReminders();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = lastShownList.get(index.getZeroBased());
        List<Condition> allConditions = model.getFilteredConditions();
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor, allConditions);

        if (!reminderToEdit.equals(editedReminder) && model.hasReminder(editedReminder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setReminder(reminderToEdit, editedReminder);
        model.updateFilteredReminders(model.PREDICATE_SHOW_ALL_REMINDERS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reinder} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Reminder createEditedReminder(Reminder reminderToEdit,
                                                 EditReminderDescriptor editReminderDescriptor,
                                                 List<Condition> allConditions) {
        assert reminderToEdit != null;
        Description updatedMessage = editReminderDescriptor.getDesc().orElse(reminderToEdit.getMessage());
        double updatedAmount = editReminderDescriptor.getQuota().orElse(reminderToEdit.getTrackerQuota());
        List<Condition> updatedCondition;
        if (editReminderDescriptor.getConditionIndices().isPresent()) {
            List<Index> conditionIndices = editReminderDescriptor.getConditionIndices().get();
            updatedCondition = conditionIndices.stream()
                    .map(index -> allConditions.get(index.getZeroBased())).collect(Collectors.toList());
        } else {
            updatedCondition = reminderToEdit.getConditions();
        }
        return new Reminder(updatedMessage, updatedCondition);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReminderCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditReminderDescriptor {
        private Description desc;
        private List<Index> conditionIndices;
        private double quota;

        public EditReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            setDesc(toCopy.desc);
            setQuota(toCopy.quota);
            setConditionIndices(toCopy.conditionIndices);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(desc, quota, conditionIndices);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }


        public void setQuota(double amt) {
            this.quota = amt;
        }

        public Optional<Double> getQuota() {
            return Optional.ofNullable(quota);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setConditionIndices(List<Index> conditionIndices) {
            if (conditionIndices != null) {
                this.conditionIndices = new ArrayList<>();
                for (Index index : conditionIndices) {
                    this.conditionIndices.add(index);
                }
            } else {
                this.conditionIndices = null;
            }
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<List<Index>> getConditionIndices() {
            return (conditionIndices != null)
                    ? Optional.of(Collections.unmodifiableList(conditionIndices)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getDesc().equals(e.getDesc())
                    && getQuota().equals(e.getQuota())
                    && getConditionIndices().equals(e.getConditionIndices());
        }
    }
}
