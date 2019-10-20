package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Splits an amount into smaller different amounts.
 */
public class SplitCommand extends Command {

    public static final String COMMAND_WORD = "split";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Splits an amount and displays your share. "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "1000 "
            + PREFIX_DATE + "02122019 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NAME + "Mary Jane ";

    private final Transaction transaction;

    public static final String MESSAGE_SUCCESS = "Split amount successful";


    public SplitCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addTransaction(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
    }

}
