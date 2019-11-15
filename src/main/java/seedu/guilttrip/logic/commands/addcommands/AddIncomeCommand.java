package seedu.guilttrip.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Income;

/**
 * Adds an income entry to guiltTrip.
 */
public class AddIncomeCommand extends Command {

    public static final String COMMAND_WORD = "addIncome";

    public static final String MESSAGE_CATEGORY = "Call the command listCategories for the list of Categories.";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Adds an income entry to guiltTrip. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Salary "
            + PREFIX_DESC + "October salary "
            + PREFIX_AMOUNT + "3000 "
            + PREFIX_DATE + "2019 10 31 "
            + PREFIX_TAG + "moneyyyy\n"
            + MESSAGE_CATEGORY;

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";

    private final Income toAdd;

    /**
     * Creates an AddIncomeCommand to add the specified {@code income}
     */
    public AddIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    /**
     * Creates an AddIncomeCommand to add the specified {@code income}
     *
     * @throws CommandException if the category of the income to be added does not exists in the list.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(toAdd.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }
        model.addIncome(toAdd);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddIncomeCommand) other).toAdd));
    }
}
