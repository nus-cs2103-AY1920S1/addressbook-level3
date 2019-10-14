package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import seedu.address.model.Model;

/**
 * Command to create a new Expense.
 */

public class ExpenseCommand extends Command {
    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds expenses to the current activity. "
            + "Parameters: "
            + PREFIX_PARTICIPANT + "NAME "
            + PREFIX_EXPENSE + "AMOUNT "
            + "[" + PREFIX_PARTICIPANT + "NAME " + PREFIX_EXPENSE + "AMOUNT ] ... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PARTICIPANT + "John Doe "
            + PREFIX_EXPENSE + "10.0 "
            + PREFIX_PARTICIPANT + "Mary "
            + PREFIX_EXPENSE + "5.0";

    public static final String MESSAGE_SUCCESS =
            "Expense of %s by %s successfully created.\n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
