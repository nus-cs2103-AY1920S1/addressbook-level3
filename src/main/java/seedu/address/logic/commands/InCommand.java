package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * Adds an income to the bank account.
 */
public class InCommand extends Command {

    public static final String COMMAND_WORD = "in";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the bank account.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_AMOUNT + "AMOUNT "
        + PREFIX_DATE + "DATE "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "milk "
        + PREFIX_AMOUNT + "100 "
        + PREFIX_DATE + "24022019 "
        + PREFIX_CATEGORY + "friends "
        + PREFIX_CATEGORY + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This transaction already exists: %1$s";
    public static final String MESSAGE_AMOUNT_OVERFLOW = "Transaction amount cannot exceed 1 million (i.e. 1000000)";
    public static final String MESSAGE_AMOUNT_NEGATIVE = "Transaction amount cannot be negative";
    public static final String MESSAGE_AMOUNT_ZERO = "Transaction amount cannot be zero";

    private final BankAccountOperation transaction;

    public InCommand(BankAccountOperation transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTransaction(transaction)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, transaction));
        } else {
            model.handleOperation(transaction);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof InCommand) {
            InCommand inCommand = (InCommand) obj;
            return transaction.equals(inCommand.transaction);
        } else {
            return false;
        }
    }
}
