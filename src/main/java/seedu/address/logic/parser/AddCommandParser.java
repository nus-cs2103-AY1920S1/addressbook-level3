package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.BloodType;
import seedu.address.profile.person.DoB;
import seedu.address.profile.person.Gender;
import seedu.address.profile.person.Height;
import seedu.address.profile.person.Name;
import seedu.address.profile.person.Person;
import seedu.address.profile.person.Weight;

/**
 * Parses input arguments and creates a new AddProfileCommand object
 */
public class AddCommandParser implements Parser<AddProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProfileCommand
     * and returns an AddProfileCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProfileCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MEDICALHISTORY, PREFIX_DOB, PREFIX_GENDER,
                        PREFIX_BLOODTYPE, PREFIX_WEIGHT, PREFIX_HEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DOB, PREFIX_GENDER, PREFIX_BLOODTYPE,
                PREFIX_WEIGHT, PREFIX_HEIGHT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProfileCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        DoB dateOfBirth = ParserUtil.parseDoB(argMultimap.getValue(PREFIX_DOB).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        BloodType bloodGroup = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get());
        Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT).get());
        Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT).get());

        Set<MedicalHistory> medicalHistoryList = ParserUtil.parseMedicalHistories(
                argMultimap.getAllValues(PREFIX_MEDICALHISTORY));

        Person person = new Person(name, dateOfBirth, gender, bloodGroup, weight, height, medicalHistoryList);

        return new AddProfileCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
