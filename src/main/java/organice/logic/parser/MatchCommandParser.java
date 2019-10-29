package organice.logic.parser;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;

import organice.logic.commands.MatchCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Nric;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    public static final String ALL = "all";
    public static final String MESSAGE_INVALID_INPUTS = "The input should be 'all' or a valid NRIC.";

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);
        String inputValue;

        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            inputValue = argMultimap.getValue(PREFIX_NRIC).get();
            checkInputArgument(inputValue);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(inputValue);
    }

    /**
     * Checks if the input value is "all" or a valid {@code Nric} value.
     */
    private boolean checkInputArgument(String inputValue) throws ParseException {
        if (inputValue.equals(ALL) || Nric.isValidNric(inputValue)) {
            return true;
        } else {
            throw new ParseException(MESSAGE_INVALID_INPUTS);
        }
    }
}
