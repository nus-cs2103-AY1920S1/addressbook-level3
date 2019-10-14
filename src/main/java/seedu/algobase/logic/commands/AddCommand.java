package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_WEBLINK;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;

/**
 * Adds a Problem to the algobase.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Problem to the algobase. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_WEBLINK + "WEBLINK "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Sequences "
            + PREFIX_AUTHOR + "Tung Kam Chuen "
            + PREFIX_WEBLINK + "https://open.kattis.com/problems/sequences "
            + PREFIX_DESCRIPTION
            + "Find the sum of the number of inversions of the 2k sequences, modulo 1000000007 (109+7). "
            + PREFIX_DIFFICULTY + "3.0 "
            + PREFIX_SOURCE + "Kattis";

    public static final String MESSAGE_SUCCESS = "New Problem added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROBLEM = "This Problem already exists in the algobase";

    private final Problem toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Problem}
     */
    public AddCommand(Problem problem) {
        requireNonNull(problem);
        toAdd = problem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProblem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROBLEM);
        }
        model.addProblem(toAdd);
        model.addTags(toAdd.getTags());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
