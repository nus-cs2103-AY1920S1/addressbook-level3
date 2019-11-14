package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.stream.Stream;

import organice.logic.commands.AddCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TaskList;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns the {@code Type} of person in the given {@code ArgumentMultimap}
     * @throws ParseException if the type is not specified correctly in the input arguments
     */
    private static Type parseType(ArgumentMultimap argumentMultimap) throws ParseException {
        List<String> allTypeInputs = argumentMultimap.getAllValues(PREFIX_TYPE);
        if (allTypeInputs.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseType(argumentMultimap.getValue(PREFIX_TYPE).get());
    }

    /**
     * Returns true if the form mode should be launched.
     * The form mode is launched if the add command only has a type prefix.
     */
    private static boolean isLaunchForm(Type type, ArgumentMultimap argumentMultimap) throws ParseException {
        return (type.isDoctor() && arePrefixesNotPresentDoctor(argumentMultimap))
                || (type.isDonor() && arePrefixesNotPresentDonor(argumentMultimap))
                || (type.isPatient() && arePrefixesNotPresentPatient(argumentMultimap));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        //put all the prefixes in the multimap to tokenize.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
                PREFIX_AGE, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                        PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN, PREFIX_TISSUE_TYPE);

        Type type = parseType(argMultimap);

        if (isLaunchForm(type, argMultimap)) {
            return new AddCommand(type);
        }

        if (type.isDoctor()) {
            arePrefixesPresentDoctor(argMultimap);
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

            Doctor doctor = new Doctor(type, nric, name, phone);
            return new AddCommand(doctor);
        } else if (type.isDonor()) {
            arePrefixesPresentDonor(argMultimap);

            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
            BloodType bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE).get());
            TissueType tissueType = ParserUtil.parseTissueType(argMultimap.getValue(PREFIX_TISSUE_TYPE).get());
            Organ organ = ParserUtil.parseOrgan(argMultimap.getValue(PREFIX_ORGAN).get());
            OrganExpiryDate organExpiryDate =
                    ParserUtil.parseOrganExpiryDate(argMultimap.getValue(PREFIX_ORGAN_EXPIRY_DATE).get());
            Status status = new Status(Status.STATUS_NOT_PROCESSING);
            TaskList taskList = new TaskList("");
            Donor donor = new Donor(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate,
                    status);
            return new AddCommand(donor);
        } else if (type.isPatient()) {
            arePrefixesPresentPatient(argMultimap);

            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            BloodType bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE).get());
            TissueType tissueType = ParserUtil.parseTissueType(argMultimap.getValue(PREFIX_TISSUE_TYPE).get());
            Organ organ = ParserUtil.parseOrgan(argMultimap.getValue(PREFIX_ORGAN).get());
            DoctorInCharge doctorInCharge =
                    ParserUtil.parseDoctorInCharge(argMultimap.getValue(PREFIX_DOCTOR_IN_CHARGE).get());
            Status status = new Status(Status.STATUS_NOT_PROCESSING);
            Patient patient = new Patient(type, nric, name, phone, age, priority,
                    bloodType, tissueType, organ, doctorInCharge, status);
            return new AddCommand(patient);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if all specified prefixes are present in the given {@code ArgumentMultimap}.
     * If there is more than one value for a prefix,
     */
    private static boolean isAValueForAPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() == 1);
    }

    /**
     * Returns true if at least one prefix contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count() != 0;
    }

    /**
     * Throws ParseException when one of the required prefixes for {@code Patient} are absent.
     */
    private static void arePrefixesPresentPatient(ArgumentMultimap argMultimap) throws ParseException {
        if (!isAValueForAPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_AGE, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_TISSUE_TYPE, PREFIX_ORGAN, PREFIX_DOCTOR_IN_CHARGE)
                        || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Throws ParseException when one of the required prefixes for Donor are absent.
     */
    private static void arePrefixesPresentDonor(ArgumentMultimap argMultimap) throws ParseException {
        if (!isAValueForAPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_AGE, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_BLOOD_TYPE, PREFIX_TISSUE_TYPE, PREFIX_ORGAN, PREFIX_ORGAN_EXPIRY_DATE)
                        || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Throws ParseException when one of the required prefixes for Doctor are absent.
     */
    private static void arePrefixesPresentDoctor(ArgumentMultimap argMultimap) throws ParseException {
        if (!isAValueForAPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if at least one prefix is specified for {@code Doctor} is present
     */
    private static boolean arePrefixesNotPresentDoctor(ArgumentMultimap argMultimap) {
        if (areAnyPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_NAME, PREFIX_PHONE)
            || !argMultimap.getPreamble().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if at least one prefix is specified for {@code Patient} is present
     */
    private static boolean arePrefixesNotPresentPatient(ArgumentMultimap argMultimap) {
        if (areAnyPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_AGE, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_TISSUE_TYPE, PREFIX_ORGAN, PREFIX_DOCTOR_IN_CHARGE)
            || !argMultimap.getPreamble().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if at least one prefix is specified for {@code Donor} is present
     */
    private static boolean arePrefixesNotPresentDonor(ArgumentMultimap argMultimap) {
        if (areAnyPrefixPresent(argMultimap, PREFIX_NRIC, PREFIX_AGE, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_ORGAN_EXPIRY_DATE, PREFIX_BLOOD_TYPE, PREFIX_TISSUE_TYPE, PREFIX_ORGAN)
            || !argMultimap.getPreamble().isEmpty()) {
            return false;
        }
        return true;
    }
}
