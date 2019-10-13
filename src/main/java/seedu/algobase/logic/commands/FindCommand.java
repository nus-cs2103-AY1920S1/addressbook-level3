package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;

/**
 * Finds and lists all problems in algobase whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all problems whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProblemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, model.getFilteredProblemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
