package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to IchiFund. "
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

    private final Transaction toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransactionCommand // instanceof handles nulls
                && toAdd.equals(((AddTransactionCommand) other).toAdd));
    }

    static public class AddTransactionCommandBuilder {
        Description description;
        Amount amount;
        Optional<Category> category;
        Optional<Day> day;
        Optional<Month> month;
        Optional<Year> year;
        Optional<TransactionType> transactionType;

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

        public AddTransactionCommand build() {
            return new AddTransactionCommand(description, amount, category, day, month, year, transactionType);
        }
    }
}
