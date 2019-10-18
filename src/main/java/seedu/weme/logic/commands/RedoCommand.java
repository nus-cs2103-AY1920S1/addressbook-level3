package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_MEMES;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Reverts the {@code model}'s meme book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo success.";
    public static final String MESSAGE_FAILURE = "No commands to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoMemeBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoMemeBook();
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
