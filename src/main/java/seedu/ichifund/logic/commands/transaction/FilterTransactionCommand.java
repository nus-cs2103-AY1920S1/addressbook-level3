package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
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
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Filters the list of transaction in IchiFund by Year, Month, and optionally Category.
 */
public class FilterTransactionCommand extends Command {

    public static final String COMMAND_WORD = "filtertx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions from a specified "
            + "month, year and optionally category, and displays them as a list with index numbers.\n"
            + "To show all categories, use "
            + PREFIX_CATEGORY + Category.CATEGORY_ALL.toString() + "\n"
            + "To show both income and expenditure items, use "
            + PREFIX_TRANSACTION_TYPE + TransactionType.TRANSACTION_TYPE_ALL.toString() + "\n"
            + "Parameters: "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_CATEGORY + "!all "
            + PREFIX_TRANSACTION_TYPE + "exp ";

    private final Month month;
    private final Year year;
    private final Optional<Category> category;
    private final Optional<TransactionType> transactionType;

    public FilterTransactionCommand(Month month, Year year, Optional<Category> category,
                                    Optional<TransactionType> transactionType) {
        this.month = month;
        this.year = year;
        this.category = category;
        this.transactionType = transactionType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTransactionContext(
                model.getTransactionContext()
                        .withMonth(month)
                        .withYear(year)
                        .withCategory(category)
                        .withType(transactionType)
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

    public static class FilterTransactionCommandBuilder {
        private Month month;
        private Year year;
        private Optional<Category> category;
        private Optional<TransactionType> transactionType;

        public void setMonth(Month month) {
            this.month = month;
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public void setCategory(Optional<Category> category) {
            this.category = category;
        }

        public void setType(Optional<TransactionType> transactionType) {
            this.transactionType = transactionType;
        }

        public FilterTransactionCommand build() {
            requireAllNonNull(month, year, category, transactionType);
            return new FilterTransactionCommand(month, year, category, transactionType);
        }
    }
}
