package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.phone.PhoneNameContainsKeywordsPredicate;

/**
 * Finds and lists all phones in phone book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPhoneCommand extends Command {

    public static final String COMMAND_WORD = "find -p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all phone whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " iphone max";

    private final PhoneNameContainsKeywordsPredicate predicate;

    public FindPhoneCommand(PhoneNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPhoneList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPhoneCommand // instanceof handles nulls
                && predicate.equals(((FindPhoneCommand) other).predicate)); // state check
    }
}
