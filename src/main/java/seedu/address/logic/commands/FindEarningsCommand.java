package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.earnings.ClassIdContainKeywordPredicate;

/**
 * Finds and lists all earnings in address book which contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEarningsCommand extends Command {

    public static final String COMMAND_WORD = "find_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all earnings whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS1231";

    private final ClassIdContainKeywordPredicate predicate;

    public FindEarningsCommand(ClassIdContainKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEarningsList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EARNINGS_LISTED_OVERVIEW, model.getFilteredEarningsList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEarningsCommand // instanceof handles nulls
                && predicate.equals(((FindEarningsCommand) other).predicate)); // state check
    }

}
