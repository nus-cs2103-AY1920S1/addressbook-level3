package seedu.jarvis.logic.commands.cca;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

public class AddCcaCommand extends Command {

    public static final String COMMAND_WORD = "add-cca";

    @Override
    public boolean hasInverseExecution() {
        return false;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from CCA command");
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
