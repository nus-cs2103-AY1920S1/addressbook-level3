package seedu.address.logic.parser.bio;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_BIO_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.bio.AddBioCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.bio.Address;
import seedu.address.model.bio.DateOfBirth;
import seedu.address.model.bio.Gender;
import seedu.address.model.bio.Goal;
import seedu.address.model.bio.MedicalCondition;
import seedu.address.model.bio.Name;
import seedu.address.model.bio.Nric;
import seedu.address.model.bio.OtherBioInfo;
import seedu.address.model.bio.Phone;
import seedu.address.model.bio.ProfileDesc;
import seedu.address.model.bio.User;

/**
 * Parses input arguments and creates a new AddBioCommand object
 */
public class AddBioCommandParser implements Parser<AddBioCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBioCommand
     * and returns an AddBioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBioCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PROFILE_DESC, PREFIX_NRIC, PREFIX_GENDER,
                        PREFIX_DATE_OF_BIRTH, PREFIX_CONTACT_NUMBER, PREFIX_EMERGENCY_CONTACT, PREFIX_MEDICAL_CONDITION,
                        PREFIX_ADDRESS, PREFIX_GOALS, PREFIX_OTHER_BIO_INFO);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CONTACT_NUMBER, PREFIX_EMERGENCY_CONTACT,
                PREFIX_MEDICAL_CONDITION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBioCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        ProfileDesc profileDesc = ParserUtil.parseProfileDesc(argMultimap.getValue(PREFIX_PROFILE_DESC));
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC));
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER));
        DateOfBirth dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE_OF_BIRTH));
        List<Phone> contactNumberList = ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_CONTACT_NUMBER));
        List<Phone> emergencyContactList = ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT));
        List<MedicalCondition> medicalConditionList = ParserUtil.parseMedicalConditions(argMultimap
                .getAllValues(PREFIX_MEDICAL_CONDITION));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
        List<Goal> goalList = ParserUtil.parseGoals(argMultimap.getAllValues(PREFIX_GOALS));
        OtherBioInfo otherInfo = ParserUtil.parseOtherBioInfo(argMultimap.getValue(PREFIX_OTHER_BIO_INFO));

        User user = new User(name, profileDesc, nric, gender, dateOfBirth, contactNumberList, emergencyContactList,
                medicalConditionList, address, goalList, otherInfo);

        return new AddBioCommand(user);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
