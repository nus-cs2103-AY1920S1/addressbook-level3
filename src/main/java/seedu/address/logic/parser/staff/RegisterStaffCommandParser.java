//@@author SakuraBlossom
package seedu.address.logic.parser.staff;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.staff.RegisterStaffCommand;
import seedu.address.logic.commands.staff.UnregisterStaffCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.Tag;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RegisterStaffCommandParser implements Parser<ReversibleActionPairCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an ReversibleActionPairCommand object containing an AddCommand for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_ID, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RegisterStaffCommand.MESSAGE_USAGE));
        }

        ReferenceId referenceId = ParserUtil.issueStaffReferenceId(argMultimap.getValue(PREFIX_ID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(""));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(referenceId, name, phone, email, address, tagList);

        return new ReversibleActionPairCommand(
            new RegisterStaffCommand(person),
            new UnregisterStaffCommand(person));
    }
}
