package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VEHICLE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VTYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVehicleCommand.EditVehicle;
import seedu.address.logic.commands.EditVehicleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditVehicleCommand object
 */
public class EditVehicleCommandParser implements Parser<EditVehicleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of EditVehicleCommand
     * and returns a EditVehicleCommand object for execution.
     * throws {@code ParseException} if the user does not conform to expected format
     */
    public EditVehicleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_DISTRICT, PREFIX_VNUM, PREFIX_VTYPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_VEHICLE_INDEX,
                    EditVehicleCommand.MESSAGE_USAGE), pe);
        }

        EditVehicle editVehicle = new EditVehicle();
        if (argMultiMap.getValue(PREFIX_DISTRICT).isPresent()) {
            editVehicle.setVehicleDistrict(ParserUtil.parseLocation(argMultiMap.getValue(PREFIX_DISTRICT).get()));
        }
        if (argMultiMap.getValue(PREFIX_VTYPE).isPresent()) {
            editVehicle.setVehicleType(ParserUtil.parseVType(argMultiMap.getValue(PREFIX_VTYPE).get()));
        }
        if (argMultiMap.getValue(PREFIX_VNUM).isPresent()) {
            editVehicle.setVehicleNumber(ParserUtil.parseVNum(argMultiMap.getValue(PREFIX_VNUM).get()));
        }

        return new EditVehicleCommand(index, editVehicle);

    }

}
