package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Budget;
import seedu.address.ui.tab.Tab;

/**
 * Sets the budget for the BankAccount.
 */
public class SetCommand extends Command {
    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a budget to the bank account.\n"
        + "Parameters: "
        + PREFIX_AMOUNT + "AMOUNT "
        + PREFIX_DATE + "DEADLINE "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_AMOUNT + "100 "
        + PREFIX_DATE + "010120120 "
        + PREFIX_CATEGORY + "food ";

    public static final String MESSAGE_SUCCESS = "New budget successfully set: %1$s";
    public static final String MESSAGE_DUPLICATE = "This budget already exists: %1$s";
    public static final String MESSAGE_AMOUNT_OVERFLOW = "Budget amount cannot exceed 1 million (i.e. 1000000)";
    public static final String MESSAGE_AMOUNT_NEGATIVE = "Budget amount cannot be negative";
    public static final String MESSAGE_AMOUNT_ZERO = "Budget amount cannot be zero";
    public static final String MESSAGE_DATE_PAST = "Budget cannot be set for past dates";

    private Budget budget;

    public SetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.has(budget)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, budget), false, false, Tab.BUDGET);
        } else {
            model.add(budget);
            //TODO: updateProjectionsAfterAdd(Budget budget)
            //model.getFilteredProjectionsList().forEach(x -> {
            //    if (x.getBudgets().isEmpty() && budget.isGeneral()) {
            //        model.deleteProjection(x);
            //        model.add(new Projection(x.getTransactionHistory(), x.getDate(),
            //                model.getFilteredBudgetList().filtered(b -> b.isGeneral())));
            //    } else if (x.getCategory() != null) {
            //        boolean categoryMatch = budget.getCategories().stream().anyMatch(c -> c.equals(x.getCategory()));
            //        if (categoryMatch) {
            //            UniqueBudgetList newBudgets = new UniqueBudgetList();
            //            model.deleteProjection(x);
            //            newBudgets.setBudgets(x.getBudgets().get());
            //            newBudgets.add(budget);
            //            model.add(new Projection(model.getFilteredTransactionList(), x.getDate(),
            //                    newBudgets.asUnmodifiableObservableList(), x.getCategory()));
            //        }
            //    }
            //});
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, budget), false, false, Tab.BUDGET);
        }
    }

}
