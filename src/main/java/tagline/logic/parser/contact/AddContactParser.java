package tagline.logic.parser.contact;

import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_ADDRESS;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_DESCRIPTION;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import tagline.logic.commands.contact.AddContactCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prefix;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.person.Address;
import tagline.model.person.Description;
import tagline.model.person.Email;
import tagline.model.person.Name;
import tagline.model.person.Person;
import tagline.model.person.Phone;

/**
 * Parses input arguments and creates a new AddContactCommand object
 */
public class AddContactParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddContactCommand
     * and returns an AddContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DESCRIPTION);

        Name name;
        Phone phone;
        Email email;
        Address address;
        Description description;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ContactParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = ContactParserUtil.parseName("");
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ContactParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            phone = ContactParserUtil.parsePhone("");
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } else {
            email = ContactParserUtil.parseEmail("");
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ContactParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        } else {
            address = ContactParserUtil.parseAddress("");
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ContactParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = ContactParserUtil.parseDescription("");
        }

        Person person = new Person(name, phone, email, address, description);

        return new AddContactCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
