package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import organice.commons.core.Messages;
import organice.model.Model;
import organice.model.person.PersonContainsPrefixesPredicate;

/**
 * Finds and lists all persons in address book whose prefixes match any of the argument prefix-keyword pairs.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose prefixes match any of "
            + "the specified prefix-keywords pairs (case-insensitive) and displays them as a list with index numbers.\n"
            + "List of Prefixes: n/, ic/, p/, a/, t/, pr/, b/, d/, tt/, exp/, o/"
            + "Parameters: PREFIX/KEYWORD [MORE_PREFIX-KEYWORD_PAIRS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice t/doctor";

    private final PersonContainsPrefixesPredicate predicate;

    public FindCommand(PersonContainsPrefixesPredicate predicate) {
        requireNonNull(predicate);
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
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
