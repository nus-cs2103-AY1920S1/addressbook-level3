package organice.logic.parser;

import static java.util.Objects.requireNonNull;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.DoneCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String prefixNricString = PREFIX_NRIC.toString();

        String firstNric;
        String secondNric;
        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords[0].startsWith(prefixNricString) && nameKeywords[1].startsWith(prefixNricString)) {
            firstNric = nameKeywords[0].replaceFirst(prefixNricString, "");
            secondNric = nameKeywords[1].replaceFirst(prefixNricString, "");
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        return new DoneCommand(firstNric, secondNric);
    }
}
