package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_SNOOZE;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.item.VisualizeList;

/**
 * Edits the details of an existing item in the item list.
 */
public class SnoozeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "snooze";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Snoozes the reminder "
            + "by the index number used in the reminder list "
            + "or the most recent occurred reminder if no index is provided.\n"
            + "Parameters: [INDEX] (must be a positive integer) "
            + "[" + PREFIX_SNOOZE + " SNOOZE TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SNOOZE + " 1.min.later";

    public static final String MESSAGE_SNOOZED_REMINDER_SUCCESS = "Snoozed Reminder: %1$s,"
            + " because someone is real lazy...";

    private static final String SHOW_REMINDER_VIEW = "R";

    private final boolean hasIndex;
    private final Index index;
    private final LocalDateTime newReminderOccurrence;

    private Item oldItem;
    private Item snoozedItem;

    /**
     * @param hasIndex              boolean that indicates if an index was specified.
     * @param index                 of the item to edit.
     * @param newReminderOccurrence LocalDateTime of new occurrence of reminder.
     */
    public SnoozeCommand(boolean hasIndex, Index index, LocalDateTime newReminderOccurrence) {
        requireAllNonNull(hasIndex, newReminderOccurrence);

        this.hasIndex = hasIndex;
        this.index = index;
        this.newReminderOccurrence = newReminderOccurrence;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        try {
            model.setVisualList(SHOW_REMINDER_VIEW);
        } catch (Exception e) {
            // should not enter here as itemType is definitely valid.
        }

        if (hasIndex) {
            VisualizeList lastShownList = model.getVisualList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
            }

            oldItem = lastShownList.get(index.getZeroBased());
            snoozedItem = oldItem.changeReminder(
                    oldItem.getReminder().get().changeOccurrenceDateTime(newReminderOccurrence));
        } else {
            try {
                oldItem = model.getLatestOccurredReminder();
            } catch (NoSuchElementException e) {
                throw new CommandException(Messages.MESSAGE_NO_PREVIOUS_REMINDER);
            }

            snoozedItem = oldItem.changeReminder(
                    oldItem.getReminder().get().changeOccurrenceDateTime(newReminderOccurrence));
        }

        model.replaceItem(oldItem, snoozedItem);
        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(String.format(MESSAGE_SNOOZED_REMINDER_SUCCESS, snoozedItem));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.replaceItem(snoozedItem, oldItem);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
