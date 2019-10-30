package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

//@@author madanalogy
/**
 * Lists all persons in the incident manager to the user.
 */
public class ListPersonsCommand extends Command {

    public static final String COMMAND_WORD = "list-a";

    public static final String MESSAGE_SUCCESS = "Listed all matching accounts!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all accounts or those whose tags match "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [KEYWORD] [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " admin";

    private final TagContainsKeywordsPredicate predicate;

    public ListPersonsCommand() {
        predicate = null;
    }

    public ListPersonsCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Objects.requireNonNullElse(predicate, PREDICATE_SHOW_ALL_PERSONS));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPersonsCommand // instanceof handles nulls
                && ((predicate == null && ((ListPersonsCommand) other).predicate == null) // if predicate is null
                || (predicate != null && predicate.equals(((ListPersonsCommand) other).predicate))));
    }
}
