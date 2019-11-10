package unrealunity.visit.logic.parser;

import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_NAME;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_PHONE;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.logic.commands.AddCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;
import unrealunity.visit.model.person.Address;
import unrealunity.visit.model.person.Email;
import unrealunity.visit.model.person.Name;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.Phone;
import unrealunity.visit.model.person.VisitList;
import unrealunity.visit.model.person.VisitReport;
import unrealunity.visit.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        // add command does not allow adding remarks straight away
        VisitList visitList = new VisitList(new ArrayList<VisitReport>());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, visitList, tagList);

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
