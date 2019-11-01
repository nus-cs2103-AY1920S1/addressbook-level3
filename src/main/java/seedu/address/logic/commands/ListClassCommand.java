package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose ClassId contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListClassCommand extends Command {

    public static final String COMMAND_WORD = "list_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose class ID contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2030 CS2040";

    private final ClassIdContainsKeywordsPredicate predicate;

    public ListClassCommand(ClassIdContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListClassCommand // instanceof handles nulls
                && predicate.equals(((ListClassCommand) other).predicate)); // state check
    }
}
