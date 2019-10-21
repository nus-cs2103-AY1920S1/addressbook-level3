package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCcaCommand extends Command {

    public static final String COMMAND_WORD = "list-cca";

    public static final String MESSAGE_SUCCESS = "Listed all Ccas";

    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";

    public static final boolean HAS_INVERSE = false;

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
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        return new CommandResult(MESSAGE_SUCCESS);
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
