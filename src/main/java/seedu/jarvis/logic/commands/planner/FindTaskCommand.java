package seedu.jarvis.logic.commands.planner;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.person.NameContainsKeywordsPredicate;

import java.util.ArrayList;

/**
 * Finds and lists all tasks in the planner whose description contains any of the argument
 * keywords.
 * Keyword matching is case insensitive.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "find-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " homework cs urgent";

    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone.";

    public static final boolean HAS_INVERSE = false;

    private final NameContainsKeywordsPredicate predicate;

    public FindTaskCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return whether the command has an inverse execution
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Finds all {@code Task} in the planner that pass the {@code Predicate} of the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code Model} of the number of {@code Task} matching the {@code Predicate}.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @throws CommandException Always thrown.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }
}
