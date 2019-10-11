package seedu.jarvis.logic.commands.planner;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

/**
 * Adds a task to JARVIS
 */
public class AddTaskCommand extends Command {

    @Override
    public boolean hasInverseExecution() {
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
