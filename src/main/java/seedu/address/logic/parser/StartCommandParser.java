package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.DifficultyEnum;

import java.util.Optional;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class StartCommandParser implements Parser<StartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();

        Optional<DifficultyEnum> difficulty = Optional.empty();

        if (trimmedArgs.isEmpty()) {
            return new StartCommand(difficulty);
        }

        switch (trimmedArgs.toUpperCase()) {
        case "EASY":
            difficulty = Optional.of(DifficultyEnum.EASY);
            break;
        case "MEDIUM":
            difficulty = Optional.of(DifficultyEnum.MEDIUM);
            break;
        case "HARD":
            difficulty = Optional.of(DifficultyEnum.HARD);
            break;
        default:
            throw new ParseException("No such difficulty " + trimmedArgs.toUpperCase());
        }
        return new StartCommand(difficulty);
    }
}
