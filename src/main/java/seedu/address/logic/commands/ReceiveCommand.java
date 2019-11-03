package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.transaction.Split;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Splits an amount into smaller different amounts.
 */
public class ReceiveCommand extends Command {

    public static final String COMMAND_WORD = "receive";
    public static final String MESSAGE_SUCCESS = "Transfer successful";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Friend pays you some money.\n"
        + "Parameters: "
        + PREFIX_AMOUNT + "AMOUNT\n"
        + PREFIX_NAME + "NAME\n"
        + "[" + PREFIX_SHARE + "SHARE]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_AMOUNT + "600 "
        + PREFIX_NAME + "John Doe \n";

    public static final String MESSAGE_DUPLICATE = "This transaction already exists: %1$s";

    private final ReceiveMoney transaction;

    public ReceiveCommand(ReceiveMoney transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.add(transaction);

        if (model.has(transaction)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, transaction));
        } else {
            model.add(transaction);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
        }
    }

}
