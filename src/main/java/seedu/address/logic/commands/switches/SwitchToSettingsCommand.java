package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;

/**
 * Class that represents a command that switches the app into Settings mode. This mode is required to
 * make changes to the difficulty of the game.
 */
public class SwitchToSettingsCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "settings";

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Now on settings page. "
            + "You can change various settings from here.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        DifficultyEnum difficulty = model.getDefaultDifficulty();
        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT
                + "\nCurrent Difficulty is: " + difficulty
                + " (" + (difficulty.getTimeAllowedPerQuestion() * 1.0) / 1000 + " seconds)");
    }

    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.SETTINGS;
    }




}
