package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Clears Mark.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mark has been cleared!";


    @Override
    public CommandResult execute(Model model, Storage storage) {
        requireNonNull(model);
        model.setMark(new Mark());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
