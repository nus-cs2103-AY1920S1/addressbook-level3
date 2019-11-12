package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.file.FullPathContainsKeywordsPredicate;

/**
 * Finds and lists all files in file book whose full path contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindFileCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all files whose file paths contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " .doc .txt";

    private final FullPathContainsKeywordsPredicate predicate;

    public FindFileCommand(FullPathContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFileList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FILES_LISTED_OVERVIEW, model.getFilteredFileList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindFileCommand // instanceof handles nulls
                && predicate.equals(((FindFileCommand) other).predicate)); // state check
    }
}
