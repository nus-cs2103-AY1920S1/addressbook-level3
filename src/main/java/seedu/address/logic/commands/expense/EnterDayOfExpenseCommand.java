package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.itinerary.day.Day;

/**
 * Command that enters the event page associated with an expense.
 */
public class EnterDayOfExpenseCommand extends Command {
    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Goes to the event list containing the expense.\n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_ENTER_DAY_SUCCESS = "Here is your day!: %1$s!";
    public static final String MESSAGE_MISC_EXPENSE = "The expense is not associated with an event";

    private final Index indexToEnter;

    public EnterDayOfExpenseCommand(Index indexToEnter) {
        this.indexToEnter = indexToEnter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            List<Expense> lastShownExpenseList = model.getPageStatus().getTrip().getExpenseList().internalList;
            List<Day> lastShownDayList = model.getPageStatus().getTrip().getDayList().internalList;

            if (indexToEnter.getZeroBased() >= lastShownExpenseList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
            }

            // References preserved by PageStatus
            Expense expenseToShow = lastShownExpenseList.get(indexToEnter.getZeroBased());
            if (expenseToShow instanceof PlannedExpense) {
                Day dayToEnter = lastShownDayList.get(expenseToShow.getDayNumber().getValue() - 1);
                model.setPageStatus(model.getPageStatus()
                        .withNewPageType(PageType.EVENT_PAGE)
                        .withNewDay(dayToEnter));
                return new CommandResult(String.format(MESSAGE_ENTER_DAY_SUCCESS, dayToEnter), true);
            } else {
                throw new ExpenseNotFoundException();
            }



        } catch (ExpenseNotFoundException ex) {
            throw new CommandException(MESSAGE_MISC_EXPENSE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterDayOfExpenseCommand;
    }

}
