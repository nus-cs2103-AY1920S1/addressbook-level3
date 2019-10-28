package seedu.elisa.logic.commands;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Item.ItemBuilder;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Priority;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.commons.util.CollectionUtil;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.item.VisualizeList;
import seedu.elisa.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.logic.parser.CliSyntax.*;

/**
 * Edits the details of an existing item in the item list.
 */
public class SnoozeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "snooze";
    public static final String SHOW_REMINDER_VIEW = "R";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Snoozes the reminder "
            + "by the index number used in the reminder list "
            + "or the most recent occurred reminder if no index is provided.\n"
            + "Parameters: [INDEX] (must be a positive integer) "
            + "[" + PREFIX_SNOOZE + "SNOOZE TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SNOOZE + "1.min.later";

    public static final String MESSAGE_SNOOZED_REMINDER_SUCCESS = "Snoozed Reminder: %1$s,"
            + " because someone is real lazy...";

    private final Index index;
    private final LocalDateTime newReminderOccurrence;

    private Item oldItem;
    private Item snoozedItem;

    /**
     * @param index of the item to edit.
     * @param newReminderOccurrence LocalDateTime of new occurrence of reminder
     */
    public SnoozeCommand(Index index, LocalDateTime newReminderOccurrence) {
        requireNonNull(index);
        requireNonNull(newReminderOccurrence);

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

        VisualizeList lastShownList = model.getVisualList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        //Here onwards
        Item oldItem = lastShownList.get(index.getZeroBased());
        this.oldItem = oldItem;
        snoozedItem = oldItem.changeReminder(
                oldItem.getReminder().get().changeOccurrenceDateTime(newReminderOccurrence));

        model.replaceItem(oldItem, snoozedItem);
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
