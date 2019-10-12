package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

import static java.util.Objects.requireNonNull;

public class SplitCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Split amount successful";
    public static final String COMMAND_WORD = "split";
    Transaction transaction;

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
