package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.ui.tab.Tab;

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
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "chicken "
        + PREFIX_AMOUNT + "5 "
        + PREFIX_DATE + "10102019 "
        + PREFIX_CATEGORY + "lunch "
        + PREFIX_CATEGORY + "foodAndBeverage";

    public static final String MESSAGE_SUCCESS = "Out transaction added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This transaction already exists: %1$s";
    public static final String MESSAGE_AMOUNT_OVERFLOW = "Transaction amount cannot exceed 1 million (i.e. 1000000)";
    public static final String MESSAGE_AMOUNT_NEGATIVE = "Transaction amount cannot be negative";
    public static final String MESSAGE_AMOUNT_ZERO = "Transaction amount cannot be zero";

    private final BankAccountOperation transaction;

    public OutCommand(BankAccountOperation transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // model.add(transaction);
        //
        // return new CommandResult(String.format(MESSAGE_SUCCESS, transaction));

        if (model.has(transaction)) {
            return new CommandResult(
                String.format(MESSAGE_DUPLICATE, transaction), false, false, Tab.TRANSACTION);
        } else {
            model.add(transaction);
            model.getFilteredProjectionsList().forEach(x -> {
                model.deleteProjection(x);
                if (x.getBudget().isPresent()) {
                    model.add(new Projection(model.getFilteredTransactionList(), x.getDate(), x.getBudget().get()));
                } else {
                    model.add(new Projection(model.getFilteredTransactionList(), x.getDate()));
                }
            });
            model.commitUserState();
            return new CommandResult(
                String.format(MESSAGE_SUCCESS, transaction), false, false, Tab.TRANSACTION);
        }
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
