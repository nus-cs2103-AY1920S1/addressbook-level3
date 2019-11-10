package seedu.elisa.logic.parser;

import seedu.elisa.logic.commands.GameCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates new GameCommand object.
 */
public class GameCommandParser implements Parser<GameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GameCommand
     * and returns a GameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GameCommand parse(String description, String empty) throws ParseException {

        if (description.trim().isEmpty()) {
            return new GameCommand("e");
        } else {
            return new GameCommand(description.trim());
        }
    }
}
