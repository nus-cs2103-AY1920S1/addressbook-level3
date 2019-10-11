package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.problem.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.problem.NameContainsKeywordsPredicate;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.SourceMatchesKeywordPredicate;
import seedu.algobase.model.problem.TagIncludesKeywordsPredicate;

/**
 * Finds and lists all problems in algobase fulfilling all the given constraints.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a problem by name, author, and/or "
            + "description and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_SOURCE + "SOURCE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_AUTHOR + "Tung Kam Chuen";
    public static final String MESSAGE_NO_CONSTRAINTS = "At least one search constraint should be provided.";

    private final Predicate<Problem> predicate;

    public FindCommand(NameContainsKeywordsPredicate namePredicate,
                       AuthorMatchesKeywordPredicate authorPredicate,
                       DescriptionContainsKeywordsPredicate descriptionPredicate,
                       SourceMatchesKeywordPredicate sourcePredicate,
                       TagIncludesKeywordsPredicate tagPredicate) {
        predicate = problem -> {
            boolean result = true;
            if (namePredicate != null) {
                result = result && namePredicate.test(problem);
            }
            if (authorPredicate != null) {
                result = result && authorPredicate.test(problem);
            }
            if (descriptionPredicate != null) {
                result = result && descriptionPredicate.test(problem);
            }
            if (sourcePredicate != null) {
                result = result && sourcePredicate.test(problem);
            }
            if (tagPredicate != null) {
                result = result && tagPredicate.test(problem);
            }
            return result;
        };
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProblemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROBLEMS_LISTED_OVERVIEW, model.getFilteredProblemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
