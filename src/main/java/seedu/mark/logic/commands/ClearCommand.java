package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;

/**
 * Clears Mark.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Mark has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMark(new Mark());
        model.saveMark();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
