package seedu.guilttrip.logic.commands.remindercommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;
import java.util.stream.Collectors;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;


/**
 * Adds a entry to the guilttrip book.
 */
public class AddGeneralReminderCommand extends Command {

    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder to reminders list. "
            + "Parameters: "
            + PREFIX_DESC + "REMINDER_MESSAGE"
            + "[" + PREFIX_INDEX + "CONDITION INDEX]..."
            + PREFIX_AMOUNT + "(Optional) Quota"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Don't be broke. "
            + PREFIX_INDEX + "1 "
            + PREFIX_AMOUNT + "100 \n";

    public static final String MESSAGE_SUCCESS = "New Reminder added: %1$s";

    private Description message;
    private List<Index> conditionIndexes;
    private Amount quota;
    private Reminder reminder;

    /**
     * Creates an AddCommand to add the specified {@code Reminder} with no tracker.
     */
    public AddGeneralReminderCommand(Description message, List<Index> conditionIndexes) {
        this.message = message;
        this.conditionIndexes = conditionIndexes;
    }

    /**
     * Creates an AddCommand to add the specified {@code Reminder} with tracker.
     */
    public AddGeneralReminderCommand(
            Description message, List<Index> conditionIndexes, Amount quota) {
        this.message = message;
        this.conditionIndexes = conditionIndexes;
        this.quota = quota;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Condition> allConditions = model.getFilteredConditions();
        List<Condition> usedConditions = conditionIndexes.stream()
                .map(index -> allConditions.get(index.getZeroBased())).collect(Collectors.toList());
        reminder = new Reminder(message, usedConditions);
        reminder.setPostDisplay(Reminder.RESETWHENDISPLAYED);
        model.addReminder(reminder);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGeneralReminderCommand // instanceof handles nulls
                && reminder.equals(((AddGeneralReminderCommand) other).reminder));
    }
}
