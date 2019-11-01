package seedu.address.logic.commands.remindercommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.reminders.conditions.EntrySpecificCondition;

/**
 * Creates a reminder for specified entry.
 */
public class SetEntryReminderCommand extends Command {

    public static final String COMMAND_WORD = "setReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a reminder for a specific entry to \n"
            + "to trigger before/ on the date of the event. \n"
            + "Parameters: "
            + PREFIX_DESC + "DESC"
            + PREFIX_TYPE + "ENTRY TYPE"
            + PREFIX_INDEX + "ENTRY INDEX"
            + PREFIX_AMOUNT + "(Optional) NO. OF..."
            + PREFIX_PARAM + "(Optional) DAYS/MONTHS/YEARS"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Don't Forget Present"
            + PREFIX_TYPE + "Expense"
            + PREFIX_INDEX + "1 "
            + PREFIX_AMOUNT + "7"
            + PREFIX_PARAM + "days \n";

    public static final String MESSAGE_SUCCESS = "Reminder Set";
    private Description message;
    private String entryType;
    private Index entryIndex;
    private int temporalQuantity;
    private TemporalUnit temporalUnit;

    public SetEntryReminderCommand(Description desc, String type, Index index, int quantity, TemporalUnit unit) {
        this.message = desc;
        this.entryType = type.toLowerCase();
        this.entryIndex = index;
        this.temporalQuantity = quantity;
        this.temporalUnit = unit;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Entry target;
        switch (entryType) {
        case "expense":
            target = model.getFilteredExpenses().get(entryIndex.getOneBased());
            break;
        case "income":
            target = model.getFilteredIncomes().get(entryIndex.getOneBased());
            break;
        case "wish":
            target = model.getFilteredWishes().get(entryIndex.getOneBased());
            break;
        case "autoexpense":
            target = model.getFilteredAutoExpenses().get(entryIndex.getOneBased());
            break;
        case "budget":
            target = model.getFilteredBudgets().get(entryIndex.getOneBased());
            break;
        default://dont use!
            throw new CommandException("Entry type not supported.");
        }
        Period bufferPeriod = Period.from(temporalUnit.getDuration()).multipliedBy(temporalQuantity);
        EntrySpecificCondition entryCondition = new EntrySpecificCondition(target, bufferPeriod);
        model.getTimeTracker().addPropertyChangeListener(entryCondition);
        target.setTracker(entryCondition);
        ArrayList<Condition> conditionWrapper = new ArrayList<Condition>();
        conditionWrapper.add(entryCondition);
        Reminder entryReminder = new Reminder(message, conditionWrapper);
        entryReminder.setEntrySpecificity(true);
        model.addReminder(entryReminder);
        return new CommandResult(String.format(MESSAGE_SUCCESS, entryReminder));
    }
}
