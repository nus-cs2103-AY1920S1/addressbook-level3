package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.repeater.RepeaterDescriptionPredicate;

/**
 * Finds and lists all repeaters in fund book whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all repeaters whose descriptions contain"
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers. If "
            + "no keyword is specified, then the search filter is cleared.\n"
            + "Parameters: [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food anime";

    private final RepeaterDescriptionPredicate predicate;

    public FindRepeaterCommand(RepeaterDescriptionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRepeaterList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_REPEATERS_LISTED_OVERVIEW, model.getFilteredRepeaterList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRepeaterCommand // instanceof handles nulls
                && predicate.equals(((FindRepeaterCommand) other).predicate)); // state check
    }
}
