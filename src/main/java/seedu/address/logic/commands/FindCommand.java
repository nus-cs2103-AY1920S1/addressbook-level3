package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.NameContainsKeywordsPredicate;
import seedu.address.ui.UiManager;

/**
 * Finds and lists all entries in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (UiManager.tab == UiManager.Tab.BODIES) {
                model.updateFilteredBodyList(predicate);
                return new CommandResult(
                        String.format(Messages.MESSAGE_WORKERS_LISTED_OVERVIEW, model.getFilteredWorkerList().size()));
            } else if (UiManager.tab == UiManager.Tab.WORKERS) {
                model.updateFilteredWorkerList(predicate);
                return new CommandResult(
                        String.format(Messages.MESSAGE_WORKERS_LISTED_OVERVIEW, model.getFilteredWorkerList().size()));
            } else {
                throw new CommandException("Please switch to Workers or Bodies tab to execute this command.");
            }
        } catch (CommandException e){
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
