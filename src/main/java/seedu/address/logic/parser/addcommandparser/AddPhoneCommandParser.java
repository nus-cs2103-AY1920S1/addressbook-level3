package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPhoneCommand object
 */
public class AddPhoneCommandParser implements Parser<AddPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPhoneCommand
     * and returns an AddPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPhoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUM, PREFIX_SERIAL_NUM, PREFIX_PHONE_NAME,
                        PREFIX_BRAND, PREFIX_CAPACITY, PREFIX_COLOUR, PREFIX_COST, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_IDENTITY_NUM, PREFIX_SERIAL_NUM, PREFIX_PHONE_NAME,
                PREFIX_BRAND, PREFIX_CAPACITY, PREFIX_COLOUR, PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhoneCommand.MESSAGE_USAGE));
        }

        IdentityNumber identityNumber = ParserUtil.parseIdentityNumber(argMultimap.getValue(PREFIX_IDENTITY_NUM).get());
        SerialNumber serialNumber = ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUM).get());
        PhoneName phoneName = ParserUtil.parsePhoneName(argMultimap.getValue(PREFIX_PHONE_NAME).get());
        Brand brand = ParserUtil.parseBrand(argMultimap.getValue(PREFIX_BRAND).get());
        Capacity capacity = ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY).get());
        Colour colour = ParserUtil.parseColour(argMultimap.getValue(PREFIX_COLOUR).get());
        Cost cost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Phone phone = new Phone(identityNumber, serialNumber, phoneName, brand, capacity, colour, cost, tagList);


        return new AddPhoneCommand(phone);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
