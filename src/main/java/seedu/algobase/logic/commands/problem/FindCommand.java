package seedu.algobase.logic.commands.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.FindProblemDescriptor;

/**
 * Finds and lists all problems in algobase fulfilling all the given constraints.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "findprob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds a problem by name, author, and/or "
            + "description and displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_SOURCE + "SOURCE] "
            + "[" + PREFIX_DIFFICULTY + "LOWER_BOUND-UPPER_BOUND] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_AUTHOR + "Tung Kam Chuen";
    public static final String MESSAGE_NO_CONSTRAINTS = "At least one search constraint should be provided.\n"
            + MESSAGE_USAGE;

    private final Predicate<Problem> predicate;
    private final FindProblemDescriptor descriptor;

    public FindCommand(FindProblemDescriptor findProblemDescriptor) {
        requireNonNull(findProblemDescriptor);
        // Creates a defensive copy of the original descriptor.
        this.descriptor = new FindProblemDescriptor(findProblemDescriptor);
        this.predicate = findProblemDescriptor.getFindProblemPredicate();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredProblemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, model.getFilteredProblemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && descriptor.equals(((FindCommand) other).descriptor)); // state check
    }

}
