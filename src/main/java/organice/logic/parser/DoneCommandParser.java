package organice.logic.parser;

import static java.util.Objects.requireNonNull;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_RESULT;

import java.util.List;

import organice.logic.commands.DoneCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExactFindCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String firstNric;
        String secondNric;

        String result;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_RESULT);

        try {
            List<String> allNricValues = argMultimap.getAllValues(PREFIX_NRIC);
            List<String> allResultValues = argMultimap.getAllValues(PREFIX_RESULT);

            if (allNricValues.size() != 2 && allResultValues.size() != 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
            }

            firstNric = allNricValues.get(0);
            secondNric = allNricValues.get(1);
            result = allResultValues.get(0);

            return new DoneCommand(firstNric, secondNric, result);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
    }
}
