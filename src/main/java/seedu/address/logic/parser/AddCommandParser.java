package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_FLAG;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.entity.body.Religion;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
        ArgumentMultimap argMultimap = tokenize(args);
        String flag = argMultimap.getValue(PREFIX_FLAG).orElse("").toLowerCase();
        boolean arePrefixesPresent;
        switch (flag) {
        case "w":
            arePrefixesPresent = arePrefixesPresentWorker(argMultimap);
            break;
        case "b":
            arePrefixesPresent = arePrefixesPresentBody(argMultimap);
            break;
        case "f":
            arePrefixesPresent = true;
            break;
        default:
            arePrefixesPresent = arePrefixesPresentPerson(argMultimap);
        }

        if (!arePrefixesPresent || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (flag.isEmpty()) {
            Name namePerson = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE_NUMBER).get());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Person person = new Person(namePerson, phone, email, address, tagList);

            return new AddCommand(person);
        }

        if (flag.equals("f")) {
            Fridge fridge = new Fridge();
            return new AddCommand(fridge);
        }
        // obtain common fields between Workers and Body
        // fields are assigned null or empty when no argument is provided
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Sex sex = ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get());
        Date dateOfBirth = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_BIRTH).orElse(""));

        if (flag.equals("w")) {
            PhoneNumber phone = ParserUtil.parsePhoneNumber(argMultimap.getValue(PREFIX_PHONE_NUMBER).orElse(""));
            Date dateJoined = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_JOINED).get());
            String designation = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_DESIGNATION).orElse(""));
            String employmentStatus = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_EMPLOYMENT_STATUS)
                    .orElse(""));
            Worker worker = new Worker(name, phone, sex, employmentStatus, dateOfBirth, dateJoined, designation);

            return new AddCommand(worker);
        } else if (flag.equals("b")) {
            Date dateOfDeath = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_DEATH).orElse(""));
            Date dateOfAdmission = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_ADMISSION).orElse(""));
            BodyStatus status = ParserUtil.parseBodyStatus(argMultimap.getValue(PREFIX_STATUS).orElse(""));
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).orElse(""));
            Name nameNok = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME_NOK).orElse(""));
            PhoneNumber phoneNok = ParserUtil.parsePhoneNumber(argMultimap.getValue(PREFIX_PHONE_NOK).orElse(""));
            String causeOfDeath = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_CAUSE_OF_DEATH)
                    .orElse(""));
            String details = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_BODY_DETAILS).orElse(""));
            List<String> organsForDonation = ParserUtil.parseOrgansForDonation(
                    argMultimap.getValue(PREFIX_ORGANS_FOR_DONATION).orElse(""));
            IdentificationNumber fridgeId = ParserUtil.parseIdentificationNumber(
                    argMultimap.getValue(PREFIX_FRIDGE_ID).orElse(""));
            Religion religion = ParserUtil.parseReligion(argMultimap.getValue(PREFIX_RELIGION).orElse(""));
            String relationship = ParserUtil.parseStringFields(argMultimap.getValue(PREFIX_RELATIONSHIP)
                    .orElse(""));

            Body body = new Body(false, 1, dateOfAdmission, name, sex, nric, religion,
                    causeOfDeath, organsForDonation, status, fridgeId, dateOfBirth, dateOfDeath, nameNok, relationship,
                            phoneNok);

            return new AddCommand(body);
        } else {
            throw new ParseException(MESSAGE_INVALID_FLAG);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean arePrefixesPresentWorker(ArgumentMultimap argumentMultimap) {
        return arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_DATE_JOINED, PREFIX_SEX);
    }

    private static boolean arePrefixesPresentBody (ArgumentMultimap argumentMultimap) {
        return arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_DATE_OF_ADMISSION,
                PREFIX_DATE_OF_DEATH, PREFIX_SEX);
    }

    private static boolean arePrefixesPresentPerson(ArgumentMultimap argumentMultimap) {
        return arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE_NUMBER,
            PREFIX_EMAIL);
    }

    /**
     * Tokenizes the input string for add command
     */
    private static ArgumentMultimap tokenize(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                        PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                                        PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                                                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                                                        PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
    }
}
