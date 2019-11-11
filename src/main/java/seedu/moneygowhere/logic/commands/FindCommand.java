package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Spending;

//@@author Nanosync
/**
 * Finds and lists all spending in MoneyGoWhere based on matching fields.
 * Conditions are stored in a predicate list.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all spending entries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Not all fields are necessary, but they are combined together to form a query.\n"
            + "Parameters: n/KEYWORD [MORE_KEYWORDS]...  d/DATE_RANGE c/COST_RANGE r/REMARK t/TAG\n"
            + "Example: " + COMMAND_WORD + " n/chicken rice d/1/1/2019 to 2/1/2019 c/1.50-5 r/tasty t/food";

    public static final String MESSAGE_DATE_RANGE_CONSTRAINTS = "Invalid date range provided.\n"
            + "You must only enter two Date values and "
            + "DATE_START must be earlier or equal to DATE_END.\n"
            + "Valid values are: today, yesterday, tomorrow or a formal date: DD/MM/YYYY, DD-MM-YYYY or YYYY-MM-DD.\n";

    public static final String MESSAGE_COST_RANGE_CONSTRAINTS = "You must enter two Cost values and "
            + "the first value cannot exceed the second value.\n"
            + "Cost must be a number with at most 2 decimal places, and it should not be blank.";

    private final List<Predicate<Spending>> predicates;

    private final Logger logger = LogsCenter.getLogger(FindCommand.class);

    public FindCommand(List<Predicate<Spending>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Optional<Predicate<Spending>> predicate = predicates.stream().reduce(Predicate::and);

        if (predicate.isEmpty()) {
            logger.warning("Predicate is empty. Showing empty list.");
            model.updateFilteredSpendingList(failed -> false);
            return new CommandResult(
                    String.format(Messages.MESSAGE_SPENDING_LISTED_OVERVIEW, model.getFilteredSpendingList().size()));
        }

        logger.info("Updated find predicate");
        model.updateFilteredSpendingList(predicate.get());
        return new CommandResult(
                String.format(Messages.MESSAGE_SPENDING_LISTED_OVERVIEW, model.getFilteredSpendingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
