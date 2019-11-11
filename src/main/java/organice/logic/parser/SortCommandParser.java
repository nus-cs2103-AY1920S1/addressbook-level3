package organice.logic.parser;

import organice.logic.commands.SortCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String ORGAN_EXPIRY_DATE = "expiry";
    public static final String PRIORITY = "priority";
    public static final String COMPATIBILITY_RATE = "rate";
    public static final String MESSAGE_INVALID_INPUTS =
            "Sort command can only take in one parameter after a match command.\n"
            + "Parameters 'expiry', 'rate' works after 'match ic/NRIC' "
            + "and 'priority' works after 'match ic/all' command\n";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        if (!(userInput.equalsIgnoreCase(ORGAN_EXPIRY_DATE)
                || userInput.equalsIgnoreCase(PRIORITY) || userInput.equalsIgnoreCase(COMPATIBILITY_RATE))) {
            throw new ParseException(MESSAGE_INVALID_INPUTS);
        }
        return new SortCommand(userInput);
    }
}
