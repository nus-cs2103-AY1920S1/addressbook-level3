package seedu.travezy.address.logic.parser;

import java.util.Set;
import java.util.stream.Stream;

import seedu.travezy.address.logic.commands.AddCommand;
import seedu.travezy.logic.parser.ArgumentMultimap;
import seedu.travezy.logic.parser.ArgumentTokenizer;
import seedu.travezy.logic.parser.Parser;
import seedu.travezy.logic.parser.ParserUtil;
import seedu.travezy.logic.parser.Prefix;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.address.model.country.Country;
import seedu.travezy.address.model.person.Address;
import seedu.travezy.address.model.person.Email;
import seedu.travezy.address.model.person.Name;
import seedu.travezy.address.model.person.Person;
import seedu.travezy.address.model.person.Phone;
import seedu.travezy.address.model.person.Remark;
import seedu.travezy.address.model.tag.Tag;
import seedu.travezy.commons.core.Messages;

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
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_TAG,
                        CliSyntax.PREFIX_COUNTRY);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get());
        Remark remark = new Remark(""); // add command does not allow adding remarks straight away
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));
        Country country = ParserUtil.parseCountry(argMultimap.getValue(CliSyntax.PREFIX_COUNTRY).orElse(""));

        Person person = new Person(name, phone, email, address, remark, tagList, country);

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
