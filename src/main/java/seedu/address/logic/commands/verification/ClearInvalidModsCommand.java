package seedu.address.logic.commands.verification;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Clears all invalid mods.
 */
public class ClearInvalidModsCommand extends Command {

    public static final String COMMAND_WORD = "clearinvalidmods";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Clearing all invalid modules";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all currently invalid modules, whose "
            + "prerequisites have not been satisfied in previous semesters.\n"
            + "Removing these modules may cause other modules that depend on it to be invalid.";
    public static final String MESSAGE_SUCCESS_PLURAL_FORMAT = "%1$d invalid modules have been cleared!";
    public static final String MESSAGE_SUCCESS_SINGULAR_FORMAT = "%1$d invalid module has been cleared!";
    public static final String MESSAGE_NO_OP = "No modules are invalid.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        int numCleared = model.clearInvalidMods();
        if (numCleared > 0) {
            model.addToHistory();
            return new CommandResult(String.format(
                    numCleared == 1 ? MESSAGE_SUCCESS_SINGULAR_FORMAT : MESSAGE_SUCCESS_PLURAL_FORMAT,
                    numCleared));
        } else {
            return new CommandResult(MESSAGE_NO_OP);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        return other instanceof ClearInvalidModsCommand;
    }
}
