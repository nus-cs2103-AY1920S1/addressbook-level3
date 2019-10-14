package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.recipe.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindRecipeCommand extends FindCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chicken noodle";

    private final NameContainsKeywordsPredicate predicate;

    public FindRecipeCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRecipeCommand // instanceof handles nulls
                && predicate.equals(((FindRecipeCommand) other).predicate)); // state check
    }
}
