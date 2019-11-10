package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.GotoCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.Storage;

/**
 * Uses a reminder to open a bookmark.
 */
public class GotoReminderCommand extends Command {

    public static final String COMMAND_WORD = "goto-reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the bookmark of the reminder with the index in the reminder list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOTO_REMINDER_ACKNOWLEDGEMENT = "Opening Reminder: %1$s";

    private final Index targetIndex;

    public GotoReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        List<Reminder> lastShownList = model.getReminders();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder target = lastShownList.get(targetIndex.getZeroBased());
        Bookmark bookmarkToOpen = model.getBookmarkFromReminder(target);
        model.setCurrentUrl(bookmarkToOpen.getUrl());

        return new GotoCommandResult(String.format(MESSAGE_GOTO_REMINDER_ACKNOWLEDGEMENT, target));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GotoReminderCommand // instanceof handles nulls
                && targetIndex.equals(((GotoReminderCommand) other).targetIndex)); // state check
    }
}

