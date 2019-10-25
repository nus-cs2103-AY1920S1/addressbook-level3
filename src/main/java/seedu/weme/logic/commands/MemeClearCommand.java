package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.model.Model;

/**
 * Clears all the memes in Weme.
 */
public class MemeClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Weme has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearMemes();
        model.commitWeme();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
