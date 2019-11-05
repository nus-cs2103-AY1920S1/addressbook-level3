package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the incident manager whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPersonsCommand extends Command {

    public static final String COMMAND_WORD = "find-a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all accounts whose names or usernames contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob agent01";

    private final NameContainsKeywordsPredicate predicate;

    public FindPersonsCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().size() == 0) {
            return new CommandResult(Messages.MESSAGE_NO_PERSON_FOUND);
        } else if (model.getFilteredPersonList().size() == 1) {
            return new CommandResult(Messages.MESSAGE_SINGLE_PERSON_LISTED);
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPersonsCommand // instanceof handles nulls
                && predicate.equals(((FindPersonsCommand) other).predicate)); // state check
    }
}
