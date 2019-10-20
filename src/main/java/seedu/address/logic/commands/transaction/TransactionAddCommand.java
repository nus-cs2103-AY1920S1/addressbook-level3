package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.DATE_EXAMPLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRENCE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Adds a transaction.
 */
public class TransactionAddCommand extends Command {

    public static final String COMMAND_WORD = "transaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction.\n"
            + "Parameters: "
            + "out|in "
            + PREFIX_AMOUNT + "<amount> "
            + PREFIX_DESCRIPTION + "<description> "
            + "[" + PREFIX_ACCOUNT + "<account>] "
            + "[" + PREFIX_CATEGORY + "<category>] "
            + "[" + PREFIX_DATE + "<date>] "
            + "[" + PREFIX_RECURRENCE + "<d|w|m|y>]\n"
            + "Example: " + COMMAND_WORD + " "
            + "out "
            + PREFIX_AMOUNT + "10 "
            + PREFIX_DESCRIPTION + "apple  "
            + PREFIX_ACCOUNT + "food  "
            + PREFIX_CATEGORY + "fruits "
            + PREFIX_DATE + DATE_EXAMPLE + " "
            + PREFIX_RECURRENCE + "d\n";

    public static final String MESSAGE_SUCCESS = "Transaction added: %1$s";
    public static final String MESSAGE_FAILURE = "Error adding transaction.";

    private final Transaction toAdd;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public TransactionAddCommand(Transaction toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getAccountBook());
        try {
            model.getAccountBook().addTransaction(toAdd);
        } catch (Exception e) {
            //TODO change to accept more specific exception
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
