package seedu.guilttrip.logic.commands.remindercommands;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.util.Frequency;

/**
 * Set a reminder for an expense/ income/ wish.
 */
public class SetReminderCommand extends Command {
    public static final String COMMAND_WORD = "setReminder";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Adds a reminder for a specific reminder/ income / wish. \n";

    public static final String UNSUPPORTED_TYPE = "Unable to create reminder for this entry.";
    public static final String INVALID_PERIOD = "Invalid period. (Cannot set reminder to notify before today!)";
    public static final String INVALID_FREQ = "Frequency must be smaller than period.";
    public static final String MESSAGE_SUCCESS = "New reminder added for: %1$s";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + " : Adds an expense entry to guiltTrip. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "Reminder header "
            + PREFIX_TYPE + "Entry type "
            + PREFIX_PERIOD + "period "
            + PREFIX_FREQ + "frequency \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TYPE + "expense "
            + PREFIX_DESC + "Mala "
            + PREFIX_PERIOD + "1d "
            + PREFIX_FREQ + "daily ";
    private final Description header;
    private final String entryType;
    private final Index targetIndex;
    private final Period period;
    private final Frequency freq;
    public SetReminderCommand(Description header, String entryType, Index targetIndex, Period period, Frequency freq) {
        this.header = header;
        this.entryType = entryType.toLowerCase();
        this.targetIndex = targetIndex;
        this.period = period;
        this.freq = freq;
    }

    @Override
    public final CommandResult execute(final Model model, final CommandHistory history) throws CommandException {
        Reminder reminder;
        Date currDate = new Date(TimeUtil.getLastRecordedDate());
        if (currDate.plus(period).isBefore(currDate.plus(freq))) {
            throw new CommandException(INVALID_FREQ);
        }
        switch(entryType) {
        case "expense":
            List<Expense> expenseList = model.getFilteredExpenses();
            Expense targetExpense = expenseList.get(targetIndex.getZeroBased());
            if (targetExpense.getDate().minus(period).isBefore(currDate)) {
                throw new CommandException(INVALID_PERIOD);
            }
            model.addReminder(new EntryReminder(header, targetExpense, period, freq));
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetExpense));
        case "income":
            List<Income> incomeList = model.getFilteredIncomes();
            Income targetIncome = incomeList.get(targetIndex.getZeroBased());
            if (targetIncome.getDate().minus(period).isBefore(currDate)) {
                throw new CommandException(INVALID_PERIOD);
            }
            model.addReminder(new EntryReminder(header, targetIncome, period, freq));
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetIncome));
        case "wish":
            List<Wish> wishList = model.getFilteredWishes();
            Wish targetWish = wishList.get(targetIndex.getZeroBased());
            if (targetWish.getDate().minus(period).isBefore(currDate)) {
                throw new CommandException(INVALID_PERIOD);
            }
            model.addReminder(new EntryReminder(header, targetWish, period, freq));
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetWish));
        default:
            throw new CommandException(UNSUPPORTED_TYPE);
        }
    }
}
