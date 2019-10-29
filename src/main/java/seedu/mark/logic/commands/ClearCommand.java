package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.results.CommandResult;
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
        requireAllNonNull(model, storage);

        model.setMark(new Mark());
        model.saveMark(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
