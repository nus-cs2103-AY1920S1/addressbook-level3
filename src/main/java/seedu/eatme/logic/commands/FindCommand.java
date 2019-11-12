package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.EateryAttributesContainsKeywordsPredicate;

/**
 * Finds and lists all eateries in eatery list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all eateries whose attributes contain any of "
            + "the specified constraints (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "{" + PREFIX_NAME + " [name]} "
            + "{" + PREFIX_ADDRESS + " [address]} "
            + "{" + PREFIX_CATEGORY + " [category]} "
            + "{" + PREFIX_TAG + " [tag]} ...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CATEGORY + " chinese " + PREFIX_TAG + " delicious";

    private final EateryAttributesContainsKeywordsPredicate predicate;

    public FindCommand(EateryAttributesContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEateryList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EATERIES_LISTED_OVERVIEW, model.getFilteredEateryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
