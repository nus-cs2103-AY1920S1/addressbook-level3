package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Finds and lists all spending in MoneyGoWhere whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all spending entries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Not all fields are necessary, but they are combined together to form a query.\n"
            + "Parameters: n/KEYWORD [MORE_KEYWORDS]...  d/DATE_RANGE c/COST_RANGE r/REMARK t/TAG\n"
            + "Example: " + COMMAND_WORD + " n/chicken rice d/1/1/2019 to 2/1/2019 c/1.50-5 r/tasty t/food";

    private final List<Predicate<Spending>> predicates;

    public FindCommand(List<Predicate<Spending>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Optional<Predicate<Spending>> predicate = predicates.stream().reduce(Predicate::and);

        if (predicate.isEmpty()) {
            model.updateFilteredSpendingList(failed -> false);
            return new CommandResult(
                    String.format(Messages.MESSAGE_SPENDINGS_LISTED_OVERVIEW, model.getFilteredSpendingList().size()));
        }

        model.updateFilteredSpendingList(predicate.get());
        return new CommandResult(
                String.format(Messages.MESSAGE_SPENDINGS_LISTED_OVERVIEW, model.getFilteredSpendingList().size()));
    }

    @Override
    public int hashCode() {
        return predicates.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
