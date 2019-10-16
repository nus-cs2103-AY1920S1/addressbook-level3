package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.Storage;

/**
 * Adds a reminder to Mark.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a reminder to Mark "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "07/01/2020 1300 "
            + PREFIX_NOTE + "Check the schedule ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This bookmark already has a reminder.";

    private final Index index;
    private final Note note;
    private final LocalDateTime time;

    /**
     * Creates an AddReminderCommand to add the specified {@code Reminder} that opens bookmark at {@code Index}.
     *
     * @param index the index of bookmark opened by the reminder.
     * @param note the note of the reminder to be added.
     * @param time the time of the reminder to be added.
     */
    public AddReminderCommand(Index index, Note note, LocalDateTime time) {
        requireAllNonNull(index, note, time);

        this.index = index;
        this.note = note;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToOpen = lastShownList.get(index.getZeroBased());
        Reminder reminderToAdd = new Reminder(bookmarkToOpen, time, note);

        if (model.isBookmarkHasReminder(bookmarkToOpen)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

        model.addReminder(bookmarkToOpen, reminderToAdd);
        model.saveMark();
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminderToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && note.equals(((AddReminderCommand) other).note)
                && time.equals(((AddReminderCommand) other).time)
                && index.equals(((AddReminderCommand) other).index));
    }
}

