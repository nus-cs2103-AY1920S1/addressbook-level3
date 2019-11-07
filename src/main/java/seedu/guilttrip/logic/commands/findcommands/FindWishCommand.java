package seedu.guilttrip.logic.commands.findcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Entry;

/**
 * Finds and lists all wishes in guilttrip book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindWishCommand extends Command {

    public static final String COMMAND_WORD = "findWish";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Finds all wish entries ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC + "which contains "
            + " the keywords that the user requests to be filtered by contain any of and displays them as a list with "
            + "index numbers.\n "
            + "[" + PREFIX_CATEGORY + "KEYWORDS] "
            + "[" + PREFIX_DESC + "KEYWORDS] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_AMOUNT + "5.60";

    public static final String INSUFFICENT_ARGUMENTS = "Find by at least one property";

    private final List<Predicate<Entry>> predicate;

    public FindWishCommand(List<Predicate<Entry>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Predicate<Entry> newPredicate = this.predicate.stream().reduce(t -> true, (tbefore, tafter) ->
                tbefore.and(tafter));
        model.updateFilteredWishes(newPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_WISHES_LISTED_OVERVIEW, model.getFilteredWishes().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindWishCommand // instanceof handles nulls
                && predicate.equals(((FindWishCommand) other).predicate)); // state check
    }
}
