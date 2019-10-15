package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.note.TitleMatchesKeywordsPredicate;

/**
 * Finds and lists all notes in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ViewNoteCommand extends Command {

    public static final String COMMAND_WORD = "view_note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all notes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " pipeline potato";

    private final TitleMatchesKeywordsPredicate predicate;

    public ViewNoteCommand(TitleMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredNoteList(predicate);
        return new CommandResult(model.getFilteredNoteList().isEmpty()
                ? Messages.MESSAGE_NO_MATCHING_NOTE_FOUND
                : Messages.MESSAGE_MATCHING_NOTE_FOUND);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewNoteCommand // instanceof handles nulls
                && predicate.equals(((ViewNoteCommand) other).predicate)); // state check
    }
}
