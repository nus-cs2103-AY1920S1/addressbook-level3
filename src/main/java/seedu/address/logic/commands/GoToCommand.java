package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class GoToCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_GOTO_ACKNOWLEDGEMENT = "Switched to %s mode.";

    private final String mode;

    public GoToCommand(String mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_GOTO_ACKNOWLEDGEMENT, mode), false, false, true, mode);
    }

}
