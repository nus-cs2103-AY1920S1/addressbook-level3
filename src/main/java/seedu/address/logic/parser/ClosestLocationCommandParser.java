package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATIONS;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.ClosestLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class is used to parse closest location command
 */
public class ClosestLocationCommandParser implements Parser<ClosestLocationCommand> {
    /**
     * This method is used to check the presence of the right prefixes
     * @param argumentMultimap
     * @param prefixes
     * @return
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public ClosestLocationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOCATIONS);

        if (!arePrefixesPresent(argMultimap, PREFIX_LOCATIONS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClosestLocationCommand.MESSAGE_USAGE));
        }
        ArrayList<String> locations = ParserUtil.parseLocations(argMultimap.getValue(PREFIX_LOCATIONS).get());
        return new ClosestLocationCommand(locations);
    }
}
