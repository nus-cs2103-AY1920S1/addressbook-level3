package seedu.address.logic.commands.settings;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gamedifficulty.DifficultyEnum;

/**
 * Class that represents a command to change the Model's difficulty for all its games.
 */
public class DifficultyCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "difficulty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes difficulty of games"
            + "Parameters: DIFFICULTY [easy/medium/hard]\n"
            + "Example: " + COMMAND_WORD + " medium";

    private final DifficultyEnum difficulty;

    public DifficultyCommand(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setDifficulty(this.difficulty);
        return new CommandResult("Difficulty is now set to: " + difficulty);
    }

}
