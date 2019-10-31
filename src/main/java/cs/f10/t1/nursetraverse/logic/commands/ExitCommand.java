package cs.f10.t1.nursetraverse.logic.commands;

import cs.f10.t1.nursetraverse.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "app-exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Patient book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
