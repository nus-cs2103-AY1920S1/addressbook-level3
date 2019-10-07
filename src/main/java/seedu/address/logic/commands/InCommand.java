package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

import static java.util.Objects.requireNonNull;

public class InCommand extends Command {

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";
    public static final String COMMAND_WORD = "in";
    Transaction transaction;

    public InCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addTransaction(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
    }
}
