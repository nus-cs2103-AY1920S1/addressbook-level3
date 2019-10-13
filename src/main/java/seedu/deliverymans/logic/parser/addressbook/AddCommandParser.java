package seedu.deliverymans.logic.parser.addressbook;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.addressbook.CliSyntax.PREFIX_EMAIL;
import static seedu.deliverymans.logic.parser.addressbook.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.addressbook.CliSyntax.PREFIX_PHONE;
import static seedu.deliverymans.logic.parser.addressbook.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.deliverymans.logic.commands.addressbook.AddCommand;
import seedu.deliverymans.logic.parser.addressbook.exceptions.ParseException;
import seedu.deliverymans.model.addressbook.person.Email;
import seedu.deliverymans.model.addressbook.person.Name;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.addressbook.person.Phone;
import seedu.deliverymans.model.addressbook.person.Remark;
import seedu.deliverymans.model.addressbook.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Remark remark = new Remark(""); // add command does not allow adding remarks straight away
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, remark, tagList);

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
