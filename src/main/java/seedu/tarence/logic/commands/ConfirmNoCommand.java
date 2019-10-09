package seedu.tarence.logic.commands;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;

/**
 * Represent the user declining a previously stored command.
 * When executed, removes the stored command from the application.
 */
public class ConfirmNoCommand extends Command {

    public static final String COMMAND_WORD = "n";

    public static final String MESSAGE_CONFIRM_NO_SUCCESS = "Command cancelled";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD, "no"};

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasPendingCommand()) {
            /*
             * While this is technically not an unknown command, from the user's point of view it is an illegal command.
             * We can only check for the validity of this command here and not in the ApplicationParser, to which the
             * model is not available. Hence, the exception is only thrown at this point.
             */
            throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
        model.getPendingCommand();
        return new CommandResult(MESSAGE_CONFIRM_NO_SUCCESS);
    }

    @Override
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}


