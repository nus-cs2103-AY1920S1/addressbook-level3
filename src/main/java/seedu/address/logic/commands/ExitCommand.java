package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResultBuilder(MESSAGE_EXIT_ACKNOWLEDGEMENT)
                .setExit().build();
    }

    @Override
    public boolean equals(Command command) {
        if (command instanceof ExitCommand) {
            return true;
        } else {
            return false;
        }
    }

}
