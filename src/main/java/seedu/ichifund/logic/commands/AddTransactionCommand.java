package seedu.ichifund.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Adds a person to the address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addtx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to IchiFund. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DAY + "DAY "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR "
            + PREFIX_CATEGORY + "CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Buy lunch "
            + PREFIX_AMOUNT + "5.28 "
            + PREFIX_DAY + "5 "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_CATEGORY + "Food ";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";

    private final Transaction toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // To implement: model.addTransaction(toAdd)
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransactionCommand // instanceof handles nulls
                && toAdd.equals(((AddTransactionCommand) other).toAdd));
    }
}