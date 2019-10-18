package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Optional;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Adds a transaction to IchiFund.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addtx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to IchiFund "
            + "and switches view to show new transaction. \n"
            + "If unspecified, transactions occur on the day of the month in system time, "
            + "and the month and year in the current list view. \n"
            + "If unspecified, the category and type of transactions follow the current list view where applicable. "
            + "Otherwise, transactions are uncategorised expenditure items by default.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DAY + "DAY] "
            + "[" + PREFIX_MONTH + "MONTH] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Buy lunch "
            + PREFIX_AMOUNT + "5.28 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_DAY + "5 "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_TRANSACTION_TYPE + "exp ";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";

    private final Description description;
    private final Amount amount;
    private final Optional<Category> category;
    private final Optional<Day> day;
    private final Optional<Month> month;
    private final Optional<Year> year;
    private final Optional<TransactionType> transactionType;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddTransactionCommand(Description description, Amount amount, Optional<Category> category,
                                 Optional<Day> day, Optional<Month> month, Optional<Year> year,
                                 Optional<TransactionType> transactionType) {
        requireAllNonNull(description, amount, category, day, month, year, transactionType);
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.day = day;
        this.month = month;
        this.year = year;
        this.transactionType = transactionType;
    }

    /**
     * Returns a {@code Transaction} object based on fields, as well as the given context.
     *
     * @param context Context from which values of unspecified fields are inferred.
     * @return {@code Transaction} object generated.
     * @throws CommandException If date generated is not valid.
     */
    private Transaction generateTransaction(TransactionContext context) throws CommandException {
        Category category = this.category.orElseGet(context::getCategory);
        Date date = generateDate(context);
        TransactionType transactionType = this.transactionType.orElseGet(context::getTransactionType);
        return new Transaction(description, amount, category, date, transactionType);
    }

    /**
     * Returns a {@code Date} object based on fields, as well as the given context.
     *
     * @param context Context form which values of unspecified fields are inferred.
     * @return {@code Date} object generated.
     * @throws CommandException If date generated is not valid.
     */
    private Date generateDate(TransactionContext context) throws CommandException {
        Day day = this.day.orElseGet(Day::getCurrent);
        Month month = this.month.orElseGet(context::getMonth);
        Year year = this.year.orElseGet(context::getYear);
        if (!Date.isValidDate(day, month, year)) {
            throw new CommandException(Date.MESSAGE_CONSTRAINTS);
        } else {
            return new Date(day, month, year);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TransactionContext context = model.getTransactionContext();
        Transaction toAdd = generateTransaction(context);
        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransactionCommand // instanceof handles nulls
                && description.equals(((AddTransactionCommand) other).description) // state check
                && amount.equals(((AddTransactionCommand) other).amount)
                && category.equals(((AddTransactionCommand) other).category)
                && day.equals(((AddTransactionCommand) other).day)
                && month.equals(((AddTransactionCommand) other).month)
                && year.equals(((AddTransactionCommand) other).year)
                && transactionType.equals(((AddTransactionCommand) other).transactionType));
    }

    /**
     * Builder class to construct an AddTransactionCommand.
     */
    public static class AddTransactionCommandBuilder {
        private Description description;
        private Amount amount;
        private Optional<Category> category;
        private Optional<Day> day;
        private Optional<Month> month;
        private Optional<Year> year;
        private Optional<TransactionType> transactionType;

        public void setDescription(Description description) {
            this.description = description;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public void setCategory(Optional<Category> category) {
            this.category = category;
        }

        public void setDay(Optional<Day> day) {
            this.day = day;
        }

        public void setMonth(Optional<Month> month) {
            this.month = month;
        }

        public void setYear(Optional<Year> year) {
            this.year = year;
        }

        public void setTransactionType(Optional<TransactionType> transactionType) {
            this.transactionType = transactionType;
        }

        /**
         * Returns a {@code AddTransactionCommand} built from the builder.
         * All fields must be non-null.
         *
         * @return The command corresponding to the builder
         */
        public AddTransactionCommand build() {
            requireAllNonNull(description, amount, category, day, month, year, transactionType);
            return new AddTransactionCommand(description, amount, category, day, month, year, transactionType);
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof AddTransactionCommandBuilder // instanceof handles nulls
                    && description.equals(((AddTransactionCommandBuilder) other).description) // state check
                    && amount.equals(((AddTransactionCommandBuilder) other).amount)
                    && category.equals(((AddTransactionCommandBuilder) other).category)
                    && day.equals(((AddTransactionCommandBuilder) other).day)
                    && month.equals(((AddTransactionCommandBuilder) other).month)
                    && year.equals(((AddTransactionCommandBuilder) other).year)
                    && transactionType.equals(((AddTransactionCommandBuilder) other).transactionType));
        }
    }
}
