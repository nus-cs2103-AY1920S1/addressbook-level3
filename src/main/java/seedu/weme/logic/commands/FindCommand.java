package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.commons.core.Messages;
import seedu.weme.model.Model;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;

/**
 * Finds and lists all memes in meme book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all memes with tags containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final TagContainsKeywordsPredicate predicate;

    public FindCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEMES_LISTED_OVERVIEW, model.getFilteredMemeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
