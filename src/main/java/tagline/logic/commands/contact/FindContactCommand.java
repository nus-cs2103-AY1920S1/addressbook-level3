package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import tagline.commons.core.Messages;
import tagline.logic.commands.CommandResult;
import tagline.model.Model;
import tagline.model.contact.NameContainsKeywordsPredicate;

/**
 * Finds and lists all contacts in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Finds all contacts whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindContactCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindContactCommand // instanceof handles nulls
                && predicate.equals(((FindContactCommand) other).predicate)); // state check
    }
}
