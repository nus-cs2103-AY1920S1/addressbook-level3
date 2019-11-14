package organice.logic.parser;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.ProcessingCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExactFindCommand object
 */
public class ProcessingCommandParser implements Parser<ProcessingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProcessingCommand
     * and returns a ProcessingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProcessingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String prefixNricString = PREFIX_NRIC.toString();

        String firstNric;
        String secondNric;
        String[] nameKeywords = trimmedArgs.split("\\s+");

        try {
            if (nameKeywords[0].startsWith(prefixNricString) && nameKeywords[1].startsWith(prefixNricString)
                && nameKeywords.length == 2) {
                firstNric = nameKeywords[0].replaceFirst(prefixNricString, "");
                secondNric = nameKeywords[1].replaceFirst(prefixNricString, "");
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingCommand.MESSAGE_USAGE));
            }
            return new ProcessingCommand(firstNric, secondNric);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingCommand.MESSAGE_USAGE));
        }
    }
}
