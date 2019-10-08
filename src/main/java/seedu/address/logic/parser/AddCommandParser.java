package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DATE_OF_BIRTH, PREFIX_POLICY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NRIC, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DATE_OF_BIRTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        boolean isInvalidNric = false;
        boolean isInvalidPhone = false;
        boolean isInvalidEmail = false;

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = null;
        try {
            nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        } catch (ParseException e) {
            isInvalidNric = true;
        }
        Phone phone = null;
        try {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (ParseException e) {
            isInvalidPhone = true;
        }
        Email email = null;
        try {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } catch (ParseException e) {
            isInvalidEmail = true;
        }
        if (isInvalidNric) {
            ParseExceptionHandler.handleNricException(name, phone, email);
        }
        if (isInvalidPhone) {
            ParseExceptionHandler.handlePhoneException(name, email);
        }
        if (isInvalidEmail) {
            ParseExceptionHandler.handleEmailException(name, phone);
        }
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).get());
        Set<Policy> policyList = ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, nric, phone, email, address, dateOfBirth, policyList, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
