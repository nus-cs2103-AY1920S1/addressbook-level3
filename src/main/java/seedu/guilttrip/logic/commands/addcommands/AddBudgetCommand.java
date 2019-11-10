package seedu.guilttrip.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Entry;

/**
 * Adds a budget entry to guiltTrip.
 */
public class AddBudgetCommand extends Command {

    public static final String COMMAND_WORD = "addBudget";

    public static final String MESSAGE_CATEGORY = "Call the command listCategories for the list of Categories.";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Adds a budget entry to the finance tracker. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "START DATE "
            + PREFIX_PERIOD + "PERIOD "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_DESC + "Nov Budget "
            + PREFIX_AMOUNT + "200 "
            + PREFIX_DATE + "2019 11 01 "
            + PREFIX_PERIOD + "1m "
            + PREFIX_TAG + "food "
            + PREFIX_TAG + "indulgence\n"
            + MESSAGE_CATEGORY;

    public static final String MESSAGE_SUCCESS = "New budget added: %1$s";

    private final Entry toAdd;

    /**
     * Creates an AddBudgetCommand to add the specified {@code entry}
     */
    public AddBudgetCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(toAdd.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }
        model.addBudget((Budget) toAdd);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
