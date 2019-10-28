package seedu.address.logic.commands.settingcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;

import static java.util.Objects.requireNonNull;

/**
 * Class that represents a command to change the Model's difficulty for all its games.
 */
public class DifficultyCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "difficulty";

    public static final String MESSAGE_USAGE = "Parameters: difficulty [easy/medium/hard]\n"
            + "Example: " + COMMAND_WORD + " medium";

    private final DifficultyEnum difficulty;

    public DifficultyCommand(DifficultyEnum difficulty) {
        requireNonNull(difficulty);
        this.difficulty = difficulty;
    }

    @Override
    public CommandResult execute(Model model) {
        model.setDefaultDifficulty(this.difficulty);
        return new CommandResult("Difficulty is now set to: " + difficulty);
    }

}
