package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.ui.tab.Tab;

/**
 * Adds an expenditure to the bank account.
 */
public class OutCommand extends Command {

    public static final String COMMAND_WORD = "out";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the bank account.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_AMOUNT + "AMOUNT "
        + PREFIX_DATE + "DATE "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "chicken "
        + PREFIX_AMOUNT + "5 "
        + PREFIX_DATE + "10102019 "
        + PREFIX_CATEGORY + "lunch "
        + PREFIX_CATEGORY + "foodAndBeverage";

    public static final String MESSAGE_SUCCESS = "Out transaction added: %1$s";

    private final BankAccountOperation transaction;

    /**
     * Creates an OutCommand to add the specified {@code BankAccountOperation}
     */
    public OutCommand(BankAccountOperation transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.add(transaction);
        model.commitUserState();
        model.updateProjectionsAfterAdd(transaction);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, transaction), false, false, Tab.TRANSACTION);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof OutCommand) {
            OutCommand outCommand = (OutCommand) obj;
            return transaction.equals(outCommand.transaction);
        } else {
            return false;
        }
    }
}
