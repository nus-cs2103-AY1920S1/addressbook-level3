package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_METHOD;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_ORDER;

import java.util.Comparator;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;

/**
 * Sort Problems in the Problem list view.
 */
public class SortCommand extends Command {

    /**
     * Possible sorting methods for {@code SortCommand}.
     */
    public enum SortingMethod {
        byName,
        byAuthor,
        byWebLink,
        byDifficulty,
        bySource;

        public static final String MESSAGE_CONSTRAINTS = "Sorting method should be one of \"name\", "
            + "\"author\", \"weblink\", \"difficulty\" or \"source\"";
    }

    /**
     * Possible sorting orders for {@code SortCommand}.
     */
    public enum SortingOrder {
        ascend,
        descend;

        public static final String MESSAGE_CONSTRAINTS = "Sorting order should be either \"ascend\" or \"descend\"";
    }

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current view in a certain order. "
            + "Parameters: "
        + PREFIX_SORTING_METHOD + "SORTING_METHOD "
            + PREFIX_SORTING_ORDER + "SORTING_ORDER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORTING_METHOD + "name "
            + PREFIX_SORTING_ORDER + "ascend";
    public static final String MESSAGE_SUCCESS = "AlgoBase has been sorted!";

    public static final Comparator<Problem> PROBLEM_NAME_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem o1, Problem o2) {
            if (o1 == o2) {
                return 0;
            }
            return o1.getName().compareTo(o2.getName());
        }
    };

    public static final Comparator<Problem> PROBLEM_AUTHOR_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem o1, Problem o2) {
            if (o1 == o2) {
                return 0;
            }
            return o1.getAuthor().compareTo(o2.getAuthor());
        }
    };

    public static final Comparator<Problem> PROBLEM_DIFFICULTY_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem o1, Problem o2) {
            if (o1 == o2) {
                return 0;
            }
            return o1.getDifficulty().compareTo(o2.getDifficulty());
        }
    };

    public static final Comparator<Problem> PROBLEM_SOURCE_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem o1, Problem o2) {
            if (o1 == o2) {
                return 0;
            }
            return o1.getSource().compareTo(o2.getSource());
        }
    };

    public static final Comparator<Problem> PROBLEM_WEB_LINK_COMPARATOR = new Comparator<Problem>() {
        @Override
        public int compare(Problem o1, Problem o2) {
            if (o1 == o2) {
                return 0;
            }
            return o1.getWebLink().compareTo(o2.getWebLink());
        }
    };

    private final SortingMethod method;
    private final boolean sortByAscendingOrder;

    public SortCommand(SortingMethod method, SortingOrder order) {
        requireAllNonNull(method, order);
        this.method = method;
        switch (order) {
        case ascend:
            this.sortByAscendingOrder = true;
            break;
        case descend:
            this.sortByAscendingOrder = false;
            break;
        default:
            throw new IllegalArgumentException("SortingOrder can be either ascend or descend");
        }
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (this.method) {
        case byName:
            if (sortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_NAME_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_NAME_COMPARATOR.reversed());
            }
            break;
        case byAuthor:
            if (sortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_AUTHOR_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_AUTHOR_COMPARATOR.reversed());
            }
            break;
        case byDifficulty:
            if (sortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_DIFFICULTY_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_DIFFICULTY_COMPARATOR.reversed());
            }
            break;
        case bySource:
            if (sortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_SOURCE_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_SOURCE_COMPARATOR.reversed());
            }
            break;
        case byWebLink:
            if (sortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_WEB_LINK_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_WEB_LINK_COMPARATOR.reversed());
            }
            break;
        default:
            throw new IllegalArgumentException("Sorting method doesn't exist.");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
