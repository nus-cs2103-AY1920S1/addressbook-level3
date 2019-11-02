package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_VTYPE;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindVehiclesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.VehicleType;

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
        ArgumentMultimap argVTypeMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_VTYPE);
        ArgumentMultimap argVNumMap = ArgumentTokenizer.tokenize(args, SEARCH_PREFIX_VNUM);

        if (arePrefixesPresent(argDistrictMap, SEARCH_PREFIX_DISTRICT)) {
            List<District> districtKeywords =
                    ParserUtil.parseDistricts(argDistrictMap.getValue(SEARCH_PREFIX_DISTRICT).get());
            return new FindVehiclesCommand(new DistrictKeywordsPredicate(districtKeywords));
        } else if (arePrefixesPresent(argVTypeMap, SEARCH_PREFIX_VTYPE)) {
            VehicleType vTypeKeywords = ParserUtil.parseVType(argVTypeMap.getValue(SEARCH_PREFIX_VTYPE).get());
            return new FindVehiclesCommand(new VTypeKeywordsPredicate(vTypeKeywords));
        } else if (arePrefixesPresent(argVNumMap, SEARCH_PREFIX_VNUM)) { // cos don't need exact match
            String vNumKeywords = argVNumMap.getValue(SEARCH_PREFIX_VNUM).get();
            assert(vNumKeywords != null);
            return new FindVehiclesCommand(new VNumKeywordsPredicate(vNumKeywords));
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
