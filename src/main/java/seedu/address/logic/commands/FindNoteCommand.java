package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TitleContainsKeywordsPredicate;

/**
 * Finds and lists all lecture notes whose title contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindNoteCommand extends Command {
    public static final String COMMAND_WORD = "findnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all lecture notes whose titles contain any of "
            + "the specified words (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: WORD [WORD]...\n"
            + "Example: " + COMMAND_WORD + " important fact";

    private final TitleContainsKeywordsPredicate predicate;

    public FindNoteCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNoteList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_NOTES_LISTED_OVERVIEW, model.getFilteredNoteList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindNoteCommand // instanceof handles nulls
                && predicate.equals(((FindNoteCommand) other).predicate)); // state check
    }
}
