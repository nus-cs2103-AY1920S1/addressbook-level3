package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATIONS;

import java.util.ArrayList;

import seedu.address.logic.commands.ClosestLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class is used to parse closest location command
 */
public class ClosestLocationCommandParser implements Parser<ClosestLocationCommand> {
    @Override
    public ClosestLocationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOCATIONS);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_LOCATIONS)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_LOCATIONS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClosestLocationCommand.MESSAGE_USAGE));
        }
        ArrayList<String> locations = ParserUtil.parseLocations(argMultimap.getValue(PREFIX_LOCATIONS).get());
        return new ClosestLocationCommand(locations);
    }
}
