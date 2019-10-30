package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddVehicleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * Parses input arguments and returns a new AddVehicleCommand object.
 */
public class AddVehicleCommandParser {

    /**
     * Parses the given arguments {@code args} and returns a new AddVehicleCommand for execution
     * @throws ParseException if user input is not in expected format.
     */
    public AddVehicleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_DISTRICT, PREFIX_VTYPE,
                PREFIX_VNUM, PREFIX_AVAIL);

        if (!arePrefixesPresent(argMultiMap, PREFIX_DISTRICT, PREFIX_VNUM, PREFIX_VTYPE, PREFIX_AVAIL)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVehicleCommand.MESSAGE_USAGE));
        }

        VehicleType type = ParserUtil.parseVType(argMultiMap.getValue(PREFIX_VTYPE).get());
        VehicleNumber number = ParserUtil.parseVNum(argMultiMap.getValue(PREFIX_VNUM).get());
        District district = ParserUtil.parseDistrict(argMultiMap.getValue(PREFIX_DISTRICT).get());
        Availability availability = ParserUtil.parseAvailability(argMultiMap.getValue(PREFIX_AVAIL).get());

        Vehicle vehicle = new Vehicle(type, number, district, availability);

        return new AddVehicleCommand(vehicle);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
