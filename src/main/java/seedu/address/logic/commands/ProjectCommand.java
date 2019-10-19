package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

public class ProjectCommand extends Command {
    public static final String COMMAND_WORD = "in";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Projects future balance based on past income/outflows.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12/12/2103";

    public static final String MESSAGE_SUCCESS = "Projected balance: %1$s";

    private final Transaction transaction;

    public ProjectCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addTransaction(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
    }
}
