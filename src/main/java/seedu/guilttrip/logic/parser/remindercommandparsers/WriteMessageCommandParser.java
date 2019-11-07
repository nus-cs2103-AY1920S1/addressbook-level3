package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.List;
import java.util.stream.Stream;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.WriteMessageCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Creates a WriteMessageCommand object.
 */
public class WriteMessageCommandParser implements Parser<WriteMessageCommand> {
    @Override
    public WriteMessageCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_COORDINATES);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_COORDINATES)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WriteMessageCommand.MESSAGE_USAGE));
        }
        String message = ParserUtil.parseDescription(argumentMultimap.getValue(PREFIX_DESC).get()).toString();
        List<Index> coordinates = ParserUtil.parseIndexes(
                argumentMultimap.getValue(PREFIX_COORDINATES).get());
        int xCoordinate = coordinates.get(0).getZeroBased();
        int yCoordinate = coordinates.get(1).getZeroBased();
        return new WriteMessageCommand(message, xCoordinate, yCoordinate);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
