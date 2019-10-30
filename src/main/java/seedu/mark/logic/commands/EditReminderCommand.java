package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.util.CollectionUtil;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;

import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;

import seedu.mark.storage.Storage;

/**
 * Edits the details of an existing reminder in Mark.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "reminder-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified."
            + "by the index number used in the displayed reminder list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_NOTE + "NOTE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "07/01/2020 1300 "
            + PREFIX_NOTE + "Check the schedule ";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);
        List<Reminder> lastShownList = model.getReminders();

        if (index.getZeroBased() >= lastShownList.size() || index.getOneBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToEdit = lastShownList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        model.editReminder(reminderToEdit, editedReminder);
        model.saveMark(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
        model.setReminders();
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder(Reminder reminderToEdit,
                                                 EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        Note updatedNote = editReminderDescriptor.getNote().orElse(reminderToEdit.getNote());
        LocalDateTime updatedTime = editReminderDescriptor.getTime().orElse(reminderToEdit.getRemindTime());
        Url url = reminderToEdit.getUrl();

        return new Reminder(url, updatedTime, updatedNote);
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
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderDescriptor {
        private Note note;
        private LocalDateTime time;

        public EditReminderDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditReminderDescriptor(EditReminderCommand.EditReminderDescriptor toCopy) {
            setNote(toCopy.note);
            setTime(toCopy.time);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(note, time);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public Optional<LocalDateTime> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderCommand.EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderCommand.EditReminderDescriptor e = (EditReminderCommand.EditReminderDescriptor) other;

            return getNote().equals(e.getNote())
                    && getTime().equals(e.getTime());
        }
    }
}
