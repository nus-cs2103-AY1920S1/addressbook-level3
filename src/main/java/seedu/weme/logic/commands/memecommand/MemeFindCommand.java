package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.commons.core.Messages;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.meme.TagContainsKeywordsPredicate;

/**
 * Finds and lists all memes in Weme whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class MemeFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": finds all memes with tags containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final TagContainsKeywordsPredicate predicate;

    public MemeFindCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof MemeFindCommand // instanceof handles nulls
                && predicate.equals(((MemeFindCommand) other).predicate)); // state check
    }
}
