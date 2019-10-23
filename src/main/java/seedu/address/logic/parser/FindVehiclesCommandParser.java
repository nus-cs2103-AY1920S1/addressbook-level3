package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DISTRICT;

import java.util.stream.Stream;

import seedu.address.logic.commands.FindVehiclesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class FindVehiclesCommandParser implements Parser<FindVehiclesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindVehiclesCommand
     * and returns a FindVehiclesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindVehiclesCommand parse(String args) throws ParseException {
        ArgumentMultimap argDistrictMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_DISTRICT);

        if (arePrefixesPresent(argDistrictMap, SEARCH_PREFIX_DISTRICT)) {
            District districtKeywords = ParserUtil.parseLocation(argDistrictMap.getValue(SEARCH_PREFIX_DISTRICT).get());
            return new FindVehiclesCommand(new DistrictKeywordsPredicate(districtKeywords));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindVehiclesCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
