package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.assignment.AssignmentNameContainsKeywordsPredicate;

//@@author weikiat97
/**
 * Finds and lists all assignment in classroom whose assignment name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "findassignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all assignments whose assignment names contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Math";

    private final AssignmentNameContainsKeywordsPredicate predicate;

    public FindAssignmentCommand(AssignmentNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(predicate);
        model.displayAssignments();
        return new CommandResult(
                String.format(Messages.MESSAGE_ASSIGNMENTS_LISTED_OVERVIEW, model.getFilteredAssignmentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAssignmentCommand // instanceof handles nulls
                && predicate.equals(((FindAssignmentCommand) other).predicate)); // state check
    }
}
