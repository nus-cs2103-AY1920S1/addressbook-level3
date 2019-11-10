package seedu.savenus.logic.parser;

import static seedu.savenus.logic.commands.ThemeCommand.MULTIPLE_THEMES_ERROR;
import static seedu.savenus.logic.commands.ThemeCommand.NO_FIELDS_ERROR;

import seedu.savenus.logic.commands.ThemeCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

//@@author robytanama
/**
 * Parses the input for command information and create a new InfoCommand object.
 */
public class ThemeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {

        // To prevent user from writing multiple themes
        String[] argsArray = args.split(" ");
        if (argsArray.length > 2) {
            throw new ParseException(
                    String.format(MULTIPLE_THEMES_ERROR + "\n" + ThemeCommand.EXAMPLE_USAGE)
            );
        }

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(NO_FIELDS_ERROR + "\n" + ThemeCommand.EXAMPLE_USAGE)
            );
        }
        return new ThemeCommand(trimmedArgs);
    }
}