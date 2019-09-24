package seedu.address.logic.parser;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {

        String[] aliasNameAndInputPair = args.strip().split("\\s+", 2);

        try {
            Alias alias = new Alias(aliasNameAndInputPair[0], aliasNameAndInputPair[1]);
            return new AliasCommand(alias);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
    }

}
