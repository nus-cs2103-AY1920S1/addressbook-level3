package organice.logic.parser;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.ProcessingMarkDoneCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExactFindCommand object
 */
public class ProcessingMarkDoneCommandParser implements Parser<ProcessingMarkDoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProcessingCommand
     * and returns a ProcessingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProcessingMarkDoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String prefixNricString = PREFIX_NRIC.toString();

        String firstNric;
        String secondNric;
        String taskNumber;

        String[] nameKeywords = trimmedArgs.split("\\s+");

        try {
            if (nameKeywords[0].startsWith(prefixNricString) && nameKeywords[1].startsWith(prefixNricString)
                && nameKeywords.length == 3) {
                firstNric = nameKeywords[0].replaceFirst(prefixNricString, "");
                secondNric = nameKeywords[1].replaceFirst(prefixNricString, "");
                taskNumber = nameKeywords[2];
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingMarkDoneCommand.MESSAGE_USAGE));
            }
            return new ProcessingMarkDoneCommand(firstNric, secondNric, taskNumber);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessingMarkDoneCommand.MESSAGE_USAGE));
        }
    }
}
