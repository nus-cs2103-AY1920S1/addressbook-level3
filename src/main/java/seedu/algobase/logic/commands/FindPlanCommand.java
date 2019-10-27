package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.searchrule.plansearchrule.FindPlanDescriptor;

/**
 * Finds and lists all plans in algobase whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPlanCommand extends Command {

    public static final String COMMAND_WORD = "findplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all plans whose names contain any of "
            + "the specified keywords (case-insensitive) or with time overlaps"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [n/PLAN_NAME] [d/PLAN_DESCRIPTION] [s/RANGE_START] [e/RANGE_END]...\n"
            + "Example: " + COMMAND_WORD + " n/CS s/2019-01-01 00:00:00 e/2019-05-04 17:00:00";

    public static final String MESSAGE_NO_CONSTRAINTS = "At least one search constraint should be provided.";

    /**
     * {@code ALWAYS_TRUE_PLAN_PREDICATE} is a non-restrictive predicate that always returns true, which
     * is used as a placeholder for predicates not provided by the user.
     */
    private static final Predicate<Plan> ALWAYS_TRUE_PLAN_PREDICATE = plan -> true;
    private final Predicate<Plan> predicate;

    public FindPlanCommand(FindPlanDescriptor findPlanDescriptor) {
        requireNonNull(findPlanDescriptor);
        this.predicate = findPlanDescriptor.getFindPlanPredicate();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlanList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PLANS_LISTED_OVERVIEW, model.getFilteredPlanList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPlanCommand // instanceof handles nulls
                && predicate.equals(((FindPlanCommand) other).predicate)); // state check
    }
}
