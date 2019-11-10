package seedu.address.logic.commands.expense.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.itinerary.day.exceptions.DayNotFoundException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * Placeholder.
 */
public class DoneEditExpenseCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your new or edited expense information.";
    public static final String MESSAGE_CREATE_EXPENSE_SUCCESS = "Created Expense: %1$s";
    public static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "Edited Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_DUPLICATED_EXPENSE = "There is an expense with the same name and "
            + "day number, please choose a different name.";
    public static final String MESSAGE_NO_MATCHING_EVENT = "The event associated with the expense is not found.";
    public static final String MESSAGE_DAY_INVALID = "The day is not found in your trip! \n"
            + " Number of days in your trip: %d";
    public static final String MESSAGE_NOT_FOUND = "The expense you are editing is not found!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor = model.getPageStatus()
                .getEditExpenseDescriptor();
        Expense expenseToEdit = model.getPageStatus().getExpense();
        Expense expenseToAdd;
        int numOfDays = model.getPageStatus().getTrip().getDayList().internalList.size();
        if (editExpenseDescriptor == null) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        try {
            CommandResult commandResult;
            if (expenseToEdit == null) {
                //buildExpense() requires compulsory fields to be non null, failing which
                //NullPointerException is caught below
                expenseToAdd = editExpenseDescriptor.buildExpense();
                if (expenseToAdd.getDayNumber().getValue() > numOfDays) {
                    throw new DayNotFoundException();
                }
                model.getPageStatus().getTrip().getExpenseList().add(expenseToAdd);
                commandResult = new CommandResult(String.format(MESSAGE_CREATE_EXPENSE_SUCCESS, expenseToAdd),
                        true);
            } else {
                //edit the current "selected" expense
                expenseToAdd = editExpenseDescriptor.buildExpense(expenseToEdit);
                if (expenseToAdd.getDayNumber().getValue() > numOfDays) {
                    throw new DayNotFoundException();
                }
                model.getPageStatus().getTrip().getExpenseList().set(expenseToEdit, expenseToAdd);
                if (expenseToAdd instanceof PlannedExpense) {
                    DayList dayList = model.getPageStatus().getTrip().getDayList();
                    Day day = dayList.get(expenseToAdd.getDayNumber().getValue() - 1);
                    day.getEventList().updateExpense(expenseToAdd);
                }
                commandResult = new CommandResult(String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, expenseToAdd),
                        true);
            }

            model.setPageStatus(model.getPageStatus()
                    .withResetEditExpenseDescriptor()
                    .withNewPageType(PageType.EXPENSE_MANAGER)
                    .withResetExpense());
            return commandResult;
        } catch (DuplicateExpenseException ex) {
            throw new CommandException(MESSAGE_DUPLICATED_EXPENSE);
        } catch (EventNotFoundException ex) {
            throw new CommandException(MESSAGE_NO_MATCHING_EVENT);
        } catch (DayNotFoundException ex) {
            throw new CommandException(String.format(MESSAGE_DAY_INVALID, numOfDays));
        } catch (ExpenseNotFoundException ex) {
            throw new CommandException(MESSAGE_NOT_FOUND);
        } catch (NullPointerException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditExpenseCommand;
    }

}
