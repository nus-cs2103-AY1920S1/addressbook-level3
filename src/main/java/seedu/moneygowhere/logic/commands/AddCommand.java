package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Adds a Spending to the MoneyGoWhere list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Spending to MoneyGoWhere. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_COST + "COST "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Apple "
            + PREFIX_DATE + "today "
            + PREFIX_COST + "1.50 "
            + PREFIX_REMARK + "Keeps the doctor away "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New Spending added: %1$s";
    public static final String MESSAGE_DUPLICATE_SPENDING = "This Spending already exists in MoneyGoWhere";

    private final Spending toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Spending spending) {
        requireNonNull(spending);
        toAdd = spending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSpending(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SPENDING);
        }

        model.addSpending(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
