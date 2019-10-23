package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AutoExpense;

/**
 * Adds a person to the address book.
 */
public class AddAutoExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addAutoExp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a auto expense to the finance tracker. " + "Parameters: " + PREFIX_TYPE
            + "TYPE " + PREFIX_DESC + "DESCRIPTION " + PREFIX_AMOUNT + "AMOUNT " + PREFIX_TIME
            + "TIME " + "[" + PREFIX_TAG + "TAG]...\n" + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Expense " + PREFIX_DESC + "Mala " + PREFIX_AMOUNT + "5.50 "
            + PREFIX_TIME + "2019-09-09 " + PREFIX_TAG + "food " + PREFIX_TAG + "indulgence";

    public static final String MESSAGE_SUCCESS = "New auto expense added: %1$s";

    private final AutoExpense toAdd;

    /**
     * Creates an AddAutoExpenseCommand to add the specified {@code Person}
     */
    public AddAutoExpenseCommand(AutoExpense autoExpense) {
        requireNonNull(autoExpense);
        toAdd = autoExpense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String type = toAdd.getType().toLowerCase();
        model.addAutoExpense(toAdd);

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAutoExpenseCommand // instanceof handles nulls
                        && toAdd.equals(((AddAutoExpenseCommand) other).toAdd));
    }
}
