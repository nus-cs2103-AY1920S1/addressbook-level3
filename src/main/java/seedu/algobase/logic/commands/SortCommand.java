package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_METHOD;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SORTING_ORDER;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

public class SortCommand extends Command {

    enum SortingMethod {
        byName,
        byAuthor,
        byWebLink,
        byDifficulty,
        bySource
    }

    enum SortingOrder {
        ascend,
        descend
    }

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current view in a certain order."
            + "Parameters: "
            + PREFIX_SORTING_METHOD + "SORTING_METHOD"
            + PREFIX_SORTING_ORDER + "SORTING_ORDER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORTING_METHOD + "time"
            + PREFIX_SORTING_ORDER + "ascend";
    public static final String MESSAGE_SUCCESS = "AlgoBase has been sorted!";

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
            // TODO: implement sorting methods
        case byName:

            break;
        case byAuthor:
            break;
        case byDifficulty:
            break;
        case bySource:
            break;
        case byWebLink:
            break;
        default:
            throw new IllegalArgumentException("Sorting method doesn't exist.");
        }
        return null;
    }
}