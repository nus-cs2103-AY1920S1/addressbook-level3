package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.MultiArgPredicate;

/**
 * Finds and lists all persons in billboard whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose attributes matches "
            + "the input parameters.\n"
            + "Parameters: "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_END_DATE + "END DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "book "
            + PREFIX_AMOUNT + "9 "
            + PREFIX_START_DATE + "25/4/2019 1200";

    private final MultiArgPredicate predicate;

    public FindCommand(MultiArgPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenses(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW, model.getFilteredExpenses().size()),
                false, false, CommandResult.DEFAULT_LIST_VIEW);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
