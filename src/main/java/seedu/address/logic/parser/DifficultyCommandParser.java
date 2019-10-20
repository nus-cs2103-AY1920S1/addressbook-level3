package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.gameCommands.GuessCommand;
import seedu.address.logic.commands.settingCommands.DifficultyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gamedifficulty.DifficultyEnum;

/**
 * Parses input arguments and creates a new GuessCommand object
 */
public class DifficultyCommandParser implements Parser<DifficultyCommand> {

    @Override
    public DifficultyCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuessCommand.MESSAGE_USAGE));
        }
        DifficultyEnum difficulty;
        switch (trimmedArgs.toUpperCase()) {
        case "EASY":
            difficulty = DifficultyEnum.EASY;
            break;
        case "MEDIUM":
            difficulty = DifficultyEnum.MEDIUM;
            break;
        case "HARD":
            difficulty = DifficultyEnum.HARD;
            break;
        default:
            throw new ParseException("No such difficulty: " + trimmedArgs.toUpperCase());
        }
        return new DifficultyCommand(difficulty);
    }
}
