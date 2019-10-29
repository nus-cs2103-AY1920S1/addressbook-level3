package thrift.logic.parser;

import static java.util.Objects.requireNonNull;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.logic.commands.CloneCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.clone.Occurrence;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX,
                CliSyntax.PREFIX_OCCURRENCE);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getIndexFromCommand());
            Occurrence occurrence = new Occurrence("daily", 1);

            if (argMultimap.getValue(CliSyntax.PREFIX_OCCURRENCE).isPresent()) {
                occurrence = ParserUtil.parseOccurrence(argMultimap.getSingleValue(CliSyntax.PREFIX_OCCURRENCE).get());
            }

            return new CloneCommand(index, occurrence);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CloneCommand.MESSAGE_USAGE), pe);
        }
    }

}
