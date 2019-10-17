package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class InfoCommandParser implements Parser<InfoCommand> {

    private static final int VALID_NUMBER_OF_ARGUMENTS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoCommand parse(String args) throws ParseException {
        if (!isInfoCommand(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE));
        }

        Index index;
        index = ParserUtil.parseIndex(extractIndex(args));
        return new InfoCommand(index);
    }

    /**
     * Extracts index from given string of arguments.
     *
     * @param args {@code String} of arguments.
     * @return {@code String} representation of index.
     */
    private String extractIndex(String args) {
        return args.trim();
    }

    /**
     * Checks if arguments are in a valid format of an info command.
     *
     * @param args {@code String} of arguments.
     * @return true if arguments are in a valid format of an info command.
     */
    private boolean isInfoCommand(String args) {
        return correctNumberOfArguments(args) //assert correct number of arguments here
                && argumentIsValidInteger(args); //asserted correct number of arguments before accessing array in here
    }

    /**
     * Checks if number of arguments correspond to that of a valid info command.
     *
     * @param args {@code String} of arguments.
     * @return true if number of arguments correspond to that of a valid info command.
     */
    private boolean correctNumberOfArguments(String args) {
        String[] argsArr = args.trim().split("\\s+");
        return argsArr.length == VALID_NUMBER_OF_ARGUMENTS;
    }

    /**
     * Checks if the given index argument is a valid integer.
     *
     * @param args {@code String} of arguments.
     * @return true if the given index argument is a valid integer.
     */
    private boolean argumentIsValidInteger(String args) {
        String arg = args.trim();
        try {
            Integer.parseInt(arg);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
