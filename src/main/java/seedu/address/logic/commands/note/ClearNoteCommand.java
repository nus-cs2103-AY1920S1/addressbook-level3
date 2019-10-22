package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AppData;
import seedu.address.model.Model;

/**
 * Clears lecture notes.
 * TODO can Shui Yao modify this so that it clears just the lecture notes?
 */
public class ClearNoteCommand extends Command {
    public static final String COMMAND_WORD = "clearnote";
    public static final String MESSAGE_SUCCESS = "Lecture notes have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAppData(new AppData());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
