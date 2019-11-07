package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_BOOL;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PARAM;

import java.util.List;
import java.util.stream.Stream;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.AddNewImageCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Creates AddNewImageCommand Object.
 */
public class AddNewImageCommandParser implements Parser<AddNewImageCommand> {
    @Override
    public AddNewImageCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_DESC, PREFIX_PARAM, PREFIX_COORDINATES, PREFIX_BOOL);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_DESC, PREFIX_PARAM, PREFIX_COORDINATES, PREFIX_BOOL)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddNewImageCommand.MESSAGE_USAGE));
        }

        String imageName = argumentMultimap.getValue(PREFIX_DESC).get();
        String imagePath = argumentMultimap.getValue(PREFIX_PARAM).get();
        List<Index> coordinates = ParserUtil.parseIndexes(argumentMultimap.getValue(PREFIX_COORDINATES).get());
        int xCoordinate = coordinates.get(0).getZeroBased();
        int yCoordinate = coordinates.get(1).getZeroBased();
        boolean willSave = ParserUtil.parseBool(argumentMultimap.getValue(PREFIX_BOOL).get());
        return new AddNewImageCommand(imageName, imagePath, xCoordinate, yCoordinate, willSave);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
