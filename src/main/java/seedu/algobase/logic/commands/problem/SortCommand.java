package seedu.algobase.logic.commands.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_METHOD;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_ORDER;

import java.util.Comparator;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
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

        public static final String KEYWORD_NAME = "name";
        public static final String KEYWORD_AUTHOR = "author";
        public static final String KEYWORD_WEBLINK = "weblink";
        public static final String KEYWORD_DIFFICULTY = "difficulty";
        public static final String KEYWORD_SOURCE = "source";
        public static final String MESSAGE_CONSTRAINTS = String.format("Sorting method should be one of "
            + "\"%1$s\", \"%2$s\", \"%3$s\", \"%4$s\" or \"%5$s\"",
            KEYWORD_NAME, KEYWORD_AUTHOR, KEYWORD_WEBLINK, KEYWORD_DIFFICULTY, KEYWORD_SOURCE);
    }

    /**
     * Possible sorting orders for {@code SortCommand}.
     */
    public enum SortingOrder {
        ascend,
        descend;

        public static final String KEYWORD_ASCEND = "ascend";
        public static final String KEYWORD_DESCEND = "descend";
        public static final String MESSAGE_CONSTRAINTS = String.format(
            "Sorting order should be either \"%1$s\" or \"%2$s\"",
            KEYWORD_ASCEND, KEYWORD_DESCEND);
    }

    public static final String COMMAND_WORD = "sortprob";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the current problem list in a certain order. "
            + "Parameters:\n"
            + PREFIX_SORTING_METHOD + "SORTING_METHOD "
            + "[" + PREFIX_SORTING_ORDER + "SORTING_ORDER]\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_SORTING_METHOD + "name "
            + PREFIX_SORTING_ORDER + "ascend";
    public static final String MESSAGE_SUCCESS = "AlgoBase has been sorted.";

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
    private final boolean isSortByAscendingOrder;

    public SortCommand(SortingMethod method, SortingOrder order) {
        requireAllNonNull(method, order);
        this.method = method;
        switch (order) {
        case ascend:
            this.isSortByAscendingOrder = true;
            break;
        case descend:
            this.isSortByAscendingOrder = false;
            break;
        default:
            throw new IllegalArgumentException("SortingOrder can be either ascend or descend");
        }
    }

    /**
     * Executes the command and returns the result message.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        switch (this.method) {
        case byName:
            if (isSortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_NAME_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_NAME_COMPARATOR.reversed());
            }
            break;
        case byAuthor:
            if (isSortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_AUTHOR_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_AUTHOR_COMPARATOR.reversed());
            }
            break;
        case byDifficulty:
            if (isSortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_DIFFICULTY_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_DIFFICULTY_COMPARATOR.reversed());
            }
            break;
        case bySource:
            if (isSortByAscendingOrder) {
                model.updateSortedProblemList(PROBLEM_SOURCE_COMPARATOR);
            } else {
                model.updateSortedProblemList(PROBLEM_SOURCE_COMPARATOR.reversed());
            }
            break;
        case byWebLink:
            if (isSortByAscendingOrder) {
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
