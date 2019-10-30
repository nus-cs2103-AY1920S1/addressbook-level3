package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.*;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.District;

/**
 * Parses input arguments and creates a new FillCommand object
 */
public class NewCommandParser implements Parser<NewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NewCommand
     * and returns a NewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format,
     * or does not only have 1 input for district.
     */
    public NewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DISTRICT,
                PREFIX_AUTO);

        // if auto/y then dont need v/
        if (!arePrefixesPresent(argMultimap, PREFIX_DISTRICT, PREFIX_AUTO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        }

        List<District> districts = ParserUtil.parseDistricts(argMultimap.getValue(PREFIX_DISTRICT).get());


        if (districts.size() != 1) {
            throw new ParseException(MESSAGE_NOT_ONE_DISTRICT);
        }
        boolean isAuto = ParserUtil.parseAuto(argMultimap.getValue(PREFIX_AUTO).get());

        if (isAuto) {
            return new NewCommand(districts.get(0), true, 0);
        } else {
            // I'm guessing it needs all prefixes tokenised
            ArgumentMultimap vArgMap = ArgumentTokenizer.tokenize(args, PREFIX_DISTRICT,
                    PREFIX_AUTO, PREFIX_VEHICLE);
            if (!arePrefixesPresent(vArgMap, PREFIX_VEHICLE)
                    || !vArgMap.getPreamble().isEmpty()) {
                return new NewCommand(districts.get(0), false, -1);
            }

            String v = vArgMap.getValue(PREFIX_VEHICLE).get();
            int indexOfV = Integer.valueOf(v);
            return new NewCommand(districts.get(0), false, indexOfV);
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
