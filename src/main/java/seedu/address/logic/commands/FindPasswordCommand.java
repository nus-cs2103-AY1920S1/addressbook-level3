package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.password.DescriptionContainsKeywordsPredicate;
import seedu.address.model.password.PasswordDescription;

/**
 * Finds and lists all password in password book whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPasswordCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all passwords whose description contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: DESCRIPTION \n"
            + PasswordDescription.MESSAGE_CONSTRAINTS;
    private final DescriptionContainsKeywordsPredicate predicate;

    public FindPasswordCommand(DescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPasswordList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PASSWORD_LISTED_OVERVIEW, model.getFilteredPasswordList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPasswordCommand // instanceof handles nulls
                && predicate.equals(((FindPasswordCommand) other).predicate)); // state check
    }
}
