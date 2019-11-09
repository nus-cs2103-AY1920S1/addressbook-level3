package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.commons.core.Messages.MESSAGE_INVALID_PERSONS_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.system.logic.commands.outofsession.AddPersonCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Gender;
import seedu.system.model.person.Name;
import seedu.system.model.person.Person;

/**
 * Parses input arguments and creates a new AddPersonCommand object.
 */
public class AddPersonCommandParser implements Parser<AddPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonCommand
     * and returns an AddPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format or {@code dateOfBirth} is invalid
     */
    public AddPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB, PREFIX_GENDER);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DOB, PREFIX_GENDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        CustomDate currDate = CustomDate.obtainCurrentDate();
        CustomDate dateOfBirth = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DOB).get());
        if (!ParserUtil.isBefore(dateOfBirth, currDate)) {
            throw new ParseException(MESSAGE_INVALID_PERSONS_DOB);
        }

        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Person person = new Person(name, dateOfBirth, gender);

        return new AddPersonCommand(person);
    }

}
