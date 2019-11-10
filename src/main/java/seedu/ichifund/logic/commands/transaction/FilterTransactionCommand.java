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
import seedu.ichifund.model.context.TransactionContext.TransactionContextBuilder;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Filters the list of transaction in IchiFund by Year, Month, and optionally Category.
 */
public class FilterTransactionCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters transactions as specified by parameters, "
            + "and displays them as a list with index numbers. "
            + "To show all categories, use "
            + "\"" + PREFIX_CATEGORY + Category.CATEGORY_ALL.toString() + "\". "
            + "To show both income and expenditure items, use "
            + "\"" + PREFIX_TRANSACTION_TYPE + TransactionType.TRANSACTION_TYPE_ALL.toString() + "\". "
            + "Parameters (at least one present): "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_CATEGORY + "!all "
            + PREFIX_TRANSACTION_TYPE + "exp ";

    private final Optional<Month> month;
    private final Optional<Year> year;
    private final Optional<Category> category;
    private final Optional<TransactionType> transactionType;

    public FilterTransactionCommand(Optional<Month> month, Optional<Year> year, Optional<Category> category,
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
                new TransactionContextBuilder(model.getTransactionContext())
                        .withMonth(month)
                        .withYear(year)
                        .withCategory(category)
                        .withType(transactionType)
                        .build()
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

    /**
     * Builder class to construct a FilterTransactionCommand.
     */
    public static class FilterTransactionCommandBuilder {
        private Optional<Month> month;
        private Optional<Year> year;
        private Optional<Category> category;
        private Optional<TransactionType> transactionType;

        /**
         * Returns a builder with the optional month specified.
         */
        public FilterTransactionCommandBuilder withMonth(Optional<Month> month) {
            this.month = month;
            return this;
        }

        /**
         * Returns a builder with the optional year specified.
         */
        public FilterTransactionCommandBuilder withYear(Optional<Year> year) {
            this.year = year;
            return this;
        }

        /**
         * Returns a builder with the optional category specified.
         */
        public FilterTransactionCommandBuilder withCategory(Optional<Category> category) {
            this.category = category;
            return this;
        }

        /**
         * Returns a builder with the optional transaction type specified.
         */
        public FilterTransactionCommandBuilder withTransactionType(Optional<TransactionType> transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        /**
         * Returns a {@code FilterTransactionCommand} built from the builder.
         * All fields must be non-null.
         *
         * @return The command corresponding to the builder
         */
        public FilterTransactionCommand build() {
            requireAllNonNull(month, year, category, transactionType);
            return new FilterTransactionCommand(month, year, category, transactionType);
        }
    }
}
