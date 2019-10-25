package thrift.logic.parser;

import static java.util.Objects.requireNonNull;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.CloneCommand;
import thrift.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class CloneCommandParser extends AddTransactionCommandParser implements Parser<CloneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CloneCommand
     * and returns a CloneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getIndexFromCommand());
            return new CloneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CloneCommand.MESSAGE_USAGE), pe);
        }
    }

}
