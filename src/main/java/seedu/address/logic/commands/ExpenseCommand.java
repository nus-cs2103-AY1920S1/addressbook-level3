package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Amount;

/**
 * Command to create a new Expense.
 */

public class ExpenseCommand extends Command {
    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds expenses to the current activity. "
            + "Parameters: "
            + PREFIX_PARTICIPANT + "NAME "
            + PREFIX_EXPENSE + "AMOUNT "
            + "[" + PREFIX_PARTICIPANT + "NAME " + PREFIX_EXPENSE + "AMOUNT ] ... "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION ]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PARTICIPANT + "John Doe "
            + PREFIX_EXPENSE + "10.0 "
            + PREFIX_PARTICIPANT + "Mary "
            + PREFIX_EXPENSE + "5.0"
            + PREFIX_DESCRIPTION + "Bubble tea";

    public static final String MESSAGE_SUCCESS = "%d expense(s) successfully created as follows:\n%s\n";
    public static final String MESSAGE_EXPENSE = "%s paid %s.\n  Description: %s\n";
    public static final String MESSAGE_INCONSISTENT_EXPENSES = "There are an unequal number of people and expenses";

    private final List<String> persons;
    private final List<Amount> amounts;
    private final String description;

    public ExpenseCommand(List<String> persons, List<Amount> amounts, String description) {
        requireAllNonNull(persons, amounts, description);
        this.persons = persons;
        this.amounts = amounts;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        /*TODO: If no activity is being viewed, the description is compulsory.
         *   A new activity will instead be created with the same title as the description
         *   (as if activity n/ACTIVITY_NAME was called).
         *   The expense and contact will then be added to the activity.
         */
        requireNonNull(model);

        if (persons.size() != amounts.size()) {
            throw new CommandException(MESSAGE_INCONSISTENT_EXPENSES);
        }

        StringBuilder successMessage = new StringBuilder();

        for (int i = 0; i < persons.size(); i++) {
            successMessage.append(String.format(MESSAGE_EXPENSE, persons.get(i), amounts.get(i), description));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, persons.size(), successMessage.toString()));
    }
}
