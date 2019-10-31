package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.note.NotesContainsKeywordsPredicate;

/**
 * Finds and lists all notes in address book whose notes contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindNotesCommand extends Command {
    public static final String COMMAND_WORD = "findnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all notes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "cs2103T";

    private final NotesContainsKeywordsPredicate predicate;

    public FindNotesCommand(NotesContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNotesList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_NOTES_LISTED_OVERVIEW, model.getFilteredNotesList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindNotesCommand // instanceof handles nulls
                && predicate.equals(((FindNotesCommand) other).predicate)); // state check
    }
}
