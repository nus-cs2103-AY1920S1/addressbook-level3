package seedu.algobase.logic.commands.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_WEBLINK;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;

/**
 * Adds a Problem to the algobase.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addprob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a Problem to AlgoBase.\n"
            + "Parameters:\n"
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_WEBLINK + "WEBLINK] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_SOURCE + "SOURCE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "Sequences "
            + PREFIX_AUTHOR + "Tung Kam Chuen\n"
            + PREFIX_WEBLINK + "https://open.kattis.com/problems/sequences\n"
            + PREFIX_DESCRIPTION
            + "Find the sum of the number of inversions of the 2k sequences, modulo 1000000007 (109+7).\n"
            + PREFIX_DIFFICULTY + "3.0 "
            + PREFIX_SOURCE + "Kattis";

    public static final String MESSAGE_SUCCESS = "New Problem [%1$s] added to AlgoBase.";
    public static final String MESSAGE_DUPLICATE_PROBLEM = "Problem [%1$s] already exists in AlgoBase.";

    private final Problem toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Problem}
     */
    public AddCommand(Problem problem) {
        requireNonNull(problem);
        toAdd = problem;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasProblem(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PROBLEM, toAdd.getName()));
        }

        model.addProblem(toAdd);
        model.addTags(toAdd.getTags());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
