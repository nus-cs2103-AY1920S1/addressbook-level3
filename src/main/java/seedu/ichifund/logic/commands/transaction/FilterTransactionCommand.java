package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Optional;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Filters the list of transaction in IchiFund by Year, Month, and optionally Category.
 */
public class FilterTransactionCommand extends Command {

    public static final String COMMAND_WORD = "filtertx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions from a specified "
            + "month, year and optionally category, and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_CATEGORY + "food ";

    private final Month month;
    private final Year year;
    private final Optional<Category> category;

    public FilterTransactionCommand(Month month, Year year, Optional<Category> category) {
        this.month = month;
        this.year = year;
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTransactionContext(
                model.getTransactionContext()
                        .getUpdatedContext(month)
                        .getUpdatedContext(year)
                        .getUpdatedContext(category)
        );
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterTransactionCommand // instanceof handles nulls
                && month.equals(((FilterTransactionCommand) other).month)
                && year.equals(((FilterTransactionCommand) other).year)
                && category.equals(((FilterTransactionCommand) other).category)); // state check
    }
}
