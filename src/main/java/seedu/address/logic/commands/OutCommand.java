package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * Adds an income to the bank account.
 */
public class OutCommand extends Command {

    public static final String COMMAND_WORD = "out";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the bank account.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "chicken "
            + PREFIX_AMOUNT + "5 "
            + PREFIX_DATE + "10102019 "
            + PREFIX_TAG + "lunch "
            + PREFIX_TAG + "foodAndBeverage";

    public static final String MESSAGE_SUCCESS = "Out transaction added: %1$s";

    private final BankAccountOperation transaction;

    public OutCommand(BankAccountOperation transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.handleOperation(transaction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
    }
}
