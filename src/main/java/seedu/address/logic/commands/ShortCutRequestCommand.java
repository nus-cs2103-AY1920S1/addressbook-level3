package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents an input by the user which is not recognised by FinSec. This essentially will be created as a command
 * and automatically seen as a request to create a shortcut.
 */
public class ShortCutRequestCommand extends Command {

    public static final String SHOWING_SHORTCUT_MESSAGE = ": There is no such command Ö "
            + "Would you like to create a shortcut? \n"
            + "If yes, enter an existing command to be a shortcut to. "
            + "Alternatively, type \"n\" to continue. ";

    private final String shortCut;

    public ShortCutRequestCommand(String shortCut) {
        this.shortCut = shortCut;
    }

    @Override
    public CommandResult execute(Model model) {
        model.saveCommand(this.shortCut);
        return new CommandResult(this.shortCut + SHOWING_SHORTCUT_MESSAGE,
                false, false, true, false, false);
    }
}
