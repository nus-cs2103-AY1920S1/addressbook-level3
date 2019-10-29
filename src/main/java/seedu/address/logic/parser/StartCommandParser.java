package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.logic.commands.switches.SwitchToStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.DifficultyEnum;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class StartCommandParser implements Parser<SwitchToStartCommand> {

    public static final String MESSAGE_NO_SUCH_DIFFICULTY = "No such difficulty: ";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SwitchToStartCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();

        Optional<DifficultyEnum> difficulty = Optional.empty();

        if (trimmedArgs.isEmpty()) {
            return new SwitchToStartCommand(difficulty);
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
            throw new ParseException(MESSAGE_NO_SUCH_DIFFICULTY + trimmedArgs.toUpperCase());
        }
        return new SwitchToStartCommand(difficulty);
    }
}
