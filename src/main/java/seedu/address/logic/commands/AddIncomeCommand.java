package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.income.Income;

/**
 * Adds an income to the address book.
 */
public class AddIncomeCommand extends Command {

    public static final String COMMAND_WORD = "add_income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an income. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_CASH + "CASH AMOUNT "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Shirt sales"
            + PREFIX_CASH + "307"
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "Marketing"
            + PREFIX_TAG + "Freshman Orientation Programme";

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This income already exists";

    private final Income toAdd;

    /**
     * Creates an AddCommand to add the specified {@code FinSec}
     */
    public AddIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasIncome(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addIncome(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddIncomeCommand) other).toAdd));
    }
}
