package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.SplitTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * Splits an amount into smaller different amounts.
 */
public class SplitCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Split amount successful";
    public static final String COMMAND_WORD = "split";
    private final SplitTransaction transaction;

    public SplitCommand(SplitTransaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addSplit(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
    }

}
