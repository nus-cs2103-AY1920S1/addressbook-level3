package seedu.address.logic.commands.settingcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.appsettings.DifficultyEnum;

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
        String outputString = String.format("Difficulty is now set to: %s (%.1f seconds)",
                difficulty,
                difficulty.getTimeAllowedPerQuestion() / 1000.0);
        return new CommandResult(outputString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DifficultyCommand // instanceof handles nulls
                && difficulty.equals(((DifficultyCommand) other).difficulty));
    }

}
