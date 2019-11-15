package seedu.address.logic.commands;

/**
 * Terminates the program.
 */
public class ExitCommand<T> extends Command<T> {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TravEzy as requested ...";

    @Override
    public CommandResult execute(T model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
