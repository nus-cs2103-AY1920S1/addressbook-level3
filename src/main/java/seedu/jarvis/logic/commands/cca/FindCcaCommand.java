package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.viewstatus.ViewType.LIST_CCA;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.CcaNameContainsKeywordsPredicate;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Finds and lists all ccas in CcaTracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCcaCommand extends Command {

    public static final String COMMAND_WORD = "find-cca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all ccas whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " canoeing guitar";

    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone.";

    public static final boolean HAS_INVERSE = false;

    private final CcaNameContainsKeywordsPredicate predicate;

    public FindCcaCommand(CcaNameContainsKeywordsPredicate predicate) {
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
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Finds all {@code Cca} in CcaTracker that pass the {@code Predicate} of the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of the number of {@code Cca} matching the {@code Predicate}.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredCcaList(predicate);
        model.setViewStatus(LIST_CCA);

        return new CommandResult(
                String.format(Messages.MESSAGE_CCAS_LISTED_OVERVIEW, model.getFilteredCcaList().size()), true);
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

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCcaCommand // instanceof handles nulls
                && predicate.equals(((FindCcaCommand) other).predicate)); // state check
    }
}
