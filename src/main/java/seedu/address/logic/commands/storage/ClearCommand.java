package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModulePlanner;

/**
 * Clears the module planner.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Module planner has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModulePlanner(new ModulePlanner());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
